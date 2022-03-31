package com.lindevhard.felia.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.slide
import com.lindevhard.felia.component.auth.AuthFlow

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun AuthFlowUi(component: AuthFlow) {
    Children(component.routerState, animation = slide()) {
        when (val child = it.instance) {
            is AuthFlow.Child.Start -> StartUi(child.component)
            is AuthFlow.Child.Create -> CreateWalletUi(child.component)
            is AuthFlow.Child.Import -> ImportWalletUi(child.component)
        }
    }
}