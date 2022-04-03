package com.lindevhard.felia.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.slide
import com.lindevhard.felia.component.main.FeliaMain
import com.lindevhard.felia.ui.wallet.WalletListUi
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainUi(component: FeliaMain) {
    Children(component.routerState, animation = slide()) {
        when (val child = it.instance) {
            is FeliaMain.Child.List -> WalletListUi(child.component)
            is FeliaMain.Child.Send -> WalletSendUi(child.component)
            is FeliaMain.Child.Receive -> WalletReceiveUi(child.component)
        }
    }

    LaunchedEffect(Unit) {
        component.events.collectLatest { event ->
            when(event) {
                FeliaMain.Event.WalletExited -> component.exitWallet()
            }
        }
    }
}