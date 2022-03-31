package com.lindevhard.felia.component.auth

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.component.import_wallet.ImportWallet
import com.lindevhard.felia.component.start.FeliaStart

interface AuthFlow {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Start(val component: FeliaStart) : Child()
        data class Import(val component: ImportWallet) : Child()
        data class Create(val component: CreateWallet) : Child()
    }
}