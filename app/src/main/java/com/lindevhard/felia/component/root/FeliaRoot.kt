package com.lindevhard.felia.component.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.lindevhard.felia.component.main.FeliaMain
import com.lindevhard.felia.component.start.FeliaStart
import com.lindevhard.felia.component.create_wallet.CreateWallet
import com.lindevhard.felia.component.import_wallet.ImportWallet

interface FeliaRoot {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Start(val component: FeliaStart) : Child()
        data class Main(val component: FeliaMain) : Child()
        data class Import(val component: ImportWallet) : Child()
        data class Create(val component: CreateWallet) : Child()
    }
}