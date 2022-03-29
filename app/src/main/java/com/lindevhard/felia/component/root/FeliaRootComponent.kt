package com.lindevhard.felia.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.component.create_wallet.CreateWalletComponent
import com.lindevhard.felia.component.import_wallet.ImportWallet
import com.lindevhard.felia.component.import_wallet.ImportWalletComponent
import com.lindevhard.felia.component.main.FeliaMainComponent
import com.lindevhard.felia.component.root.FeliaRoot.Child
import com.lindevhard.felia.component.start.FeliaStartComponent
import kotlinx.parcelize.Parcelize

class FeliaRootComponent internal constructor(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
) : FeliaRoot, ComponentContext by componentContext {

    private val router: Router<Configuration, Child> =
        router(
            initialConfiguration = Configuration.Start,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Start -> Child.Start(
                FeliaStartComponent(
                    onCreateWallet = { router.push(Configuration.CreateWallet) },
                    onImportWallet = { router.push(Configuration.ImportWallet) }
                )
            )
            is Configuration.Main ->
                Child.Main(FeliaMainComponent())
            Configuration.CreateWallet ->
                Child.Create(CreateWalletComponent(::onCreateWalletOutput))
            Configuration.ImportWallet ->
                Child.Import(
                    ImportWalletComponent(
                        componentContext,
                        storeFactory = storeFactory,
                        ::onImportWalletOutput
                    )
                )
        }

    private fun onCreateWalletOutput(output: CreateWallet.Output) {
        when (output) {
            CreateWallet.Output.Closed -> router.pop()
        }
    }

    private fun onImportWalletOutput(output: ImportWallet.Output) {
        when (output) {
            ImportWallet.Output.Closed -> router.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Start : Configuration()

        @Parcelize
        object ImportWallet : Configuration()

        @Parcelize
        object CreateWallet : Configuration()

        @Parcelize
        data class Main(val itemId: Long) : Configuration()
    }

    sealed interface DeepLink {
        object None : DeepLink
        class Web(val path: String) : DeepLink
    }
}