package com.lindevhard.felia.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.slide
import com.lindevhard.felia.component.main.FeliaMain

@Composable
fun MainUi(component: FeliaMain) {
    Children(component.routerState, animation = slide()) {
        when (val child = it.instance) {
            is FeliaMain.Child.List -> WalletListUi(child.component)
            is FeliaMain.Child.Send -> WalletSendUi(child.component)
            is FeliaMain.Child.Receive -> WalletReceiveUi(child.component)
        }
    }
}