package com.lindevhard.felia.component.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.component.auth.AuthFlow
import com.lindevhard.felia.component.main.FeliaMain

interface FeliaRoot {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Main(val component: FeliaMain) : Child()
        data class Auth(val component: AuthFlow): Child()
    }
}