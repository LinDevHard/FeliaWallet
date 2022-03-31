package com.lindevhard.felia.component.auth.di

import com.arkivanov.decompose.ComponentContext
import com.lindevhard.felia.component.auth.AuthFlow
import com.lindevhard.felia.component.auth.AuthFlowComponent
import org.koin.dsl.module

val authFlowModule = module {
    factory<AuthFlow> { (componentContext: ComponentContext, onComplete: () -> Unit)  ->
        AuthFlowComponent(componentContext = componentContext, onCompleteAuth = onComplete)
    }
}