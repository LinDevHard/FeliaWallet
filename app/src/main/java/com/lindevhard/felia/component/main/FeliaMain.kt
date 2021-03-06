package com.lindevhard.felia.component.main

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.component.wallet.detail.WalletDetail
import com.lindevhard.felia.component.wallet.list.WalletList
import com.lindevhard.felia.component.wallet.receive.WalletReceive
import com.lindevhard.felia.component.wallet.send.WalletSend
import kotlinx.coroutines.flow.Flow

interface FeliaMain {

    val routerState: Value<RouterState<*, Child>>

    val events: Flow<Event>

    fun exitWallet()

    sealed class Child {
        data class List(val component: WalletList) : Child()
        data class Receive(val component: WalletReceive) : Child()
        data class Send(val component: WalletSend) : Child()
        data class Detail(val component: WalletDetail) : Child()
    }

    sealed class Event {
        object WalletExited: Event()
    }
}