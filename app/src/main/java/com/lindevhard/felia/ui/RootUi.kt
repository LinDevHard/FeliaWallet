package com.lindevhard.felia.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.slide
import com.lindevhard.felia.component.root.FeliaRoot
import com.lindevhard.felia.component.root.FeliaRoot.Child

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootUi(root: FeliaRoot) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Children(root.routerState, animation = slide()) {
            when (val child = it.instance) {
                is Child.Main -> MainUi(child.component)
                is Child.Auth -> AuthFlowUi(child.component)
            }
        }
    }
}