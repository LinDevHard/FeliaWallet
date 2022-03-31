package com.lindevhard.felia.component.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.lindevhard.felia.component.auth.AuthFlow.Child
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.component.create_wallet.CreateWalletComponent
import com.lindevhard.felia.component.import_wallet.ImportWallet
import com.lindevhard.felia.component.start.FeliaStartComponent
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class AuthFlowComponent(
    componentContext: ComponentContext,
    private val onCompleteAuth: () -> Unit,
) : AuthFlow, KoinComponent, ComponentContext by componentContext {

    private val router: Router<Configuration, Child> = router(
        initialConfiguration = Configuration.Start,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state


    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =   when (configuration) {
        is Configuration.Start -> Child.Start(
            FeliaStartComponent(
                onCreateWallet = { router.push(Configuration.CreateWallet) },
                onImportWallet = { router.push(Configuration.ImportWallet) }
            )
        )
        Configuration.CreateWallet -> {
            Child.Create(CreateWalletComponent(::onCreateWalletOutput))
        }
        Configuration.ImportWallet -> {
            val importWalletComponent by inject<ImportWallet> {
                parametersOf(componentContext, ::onImportWalletOutput)
            }

            Child.Import(importWalletComponent)
        }
    }

    private fun onCreateWalletOutput(output: CreateWallet.Output) {
        when (output) {
            CreateWallet.Output.Closed -> router.pop()
        }
    }

    private fun onImportWalletOutput(output: ImportWallet.Output) {
        when (output) {
            ImportWallet.Output.Closed -> {
                router.pop()
            }
            ImportWallet.Output.Imported -> {
                onCompleteAuth()
            }
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Start : Configuration()

        @Parcelize
        object ImportWallet : Configuration()

        @Parcelize
        object CreateWallet : Configuration()
    }
}