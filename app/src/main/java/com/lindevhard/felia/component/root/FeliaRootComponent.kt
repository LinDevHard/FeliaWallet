package com.lindevhard.felia.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.gexabyte.android.wallet.core.domain.InitWalletRepository
import com.lindevhard.felia.component.auth.AuthFlow
import com.lindevhard.felia.component.main.FeliaMain
import com.lindevhard.felia.component.main.FeliaMainComponent
import com.lindevhard.felia.component.root.FeliaRoot.Child
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class FeliaRootComponent internal constructor(
    componentContext: ComponentContext,
    private val walletRepository: InitWalletRepository,
) : FeliaRoot, KoinComponent, ComponentContext by componentContext {

    private val startDestination : Configuration by lazy {
        runBlocking {
            if (walletRepository.isWalletSaved())
                Configuration.Main
            else
                Configuration.AuthFlow
        }
    }

    private val router: Router<Configuration, Child> = router(
            initialConfiguration = startDestination,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Main -> {
                val mainFlowComponent by inject<FeliaMain> {
                    parametersOf(componentContext, ::navigateToAuth)
                }

                Child.Main(mainFlowComponent)
            }
            Configuration.AuthFlow -> {
                val authFlowComponent by inject<AuthFlow> {
                    parametersOf(componentContext, ::navigateToMain)
                }

                Child.Auth(authFlowComponent)
            }
        }

    private fun navigateToMain() {
        router.replaceCurrent(Configuration.Main)
    }

    private fun navigateToAuth() {
        router.replaceCurrent(Configuration.AuthFlow)
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object AuthFlow : Configuration()

        @Parcelize
        object Main : Configuration()
    }
}