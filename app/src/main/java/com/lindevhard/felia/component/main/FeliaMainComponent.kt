package com.lindevhard.felia.component.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.lindevhard.felia.component.main.FeliaMain.Child
import com.lindevhard.felia.component.wallet.list.WalletListComponent
import com.lindevhard.felia.component.wallet.receive.WalletReceiveComponent
import com.lindevhard.felia.component.wallet.send.WalletSendComponent
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent

class FeliaMainComponent(
    componentContext: ComponentContext,
) : FeliaMain, KoinComponent, ComponentContext by componentContext {


    private val router: Router<Configuration, Child> = router(
        initialConfiguration = Configuration.List,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child = when (configuration) {
        Configuration.List -> Child.List(WalletListComponent())
        Configuration.Receive -> Child.Receive(WalletReceiveComponent())
        Configuration.Send -> Child.Send(WalletSendComponent())
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        object Receive : Configuration()

        @Parcelize
        object Send : Configuration()
    }
}
