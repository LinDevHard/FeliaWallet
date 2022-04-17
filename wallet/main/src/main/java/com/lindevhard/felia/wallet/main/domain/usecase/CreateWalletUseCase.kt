package com.lindevhard.felia.wallet.main.domain.usecase

import com.gexabyte.android.wallet.assets.AssetsRepository
import com.gexabyte.android.wallet.core.domain.MemoryStorage
import com.lindevhard.felia.wallet.main.domain.WalletRepository
import com.lindevhard.felia.wallet.main.domain.model.CreateWalletRequest
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet

class CreateWalletUseCase(
    private val assetsRepository: AssetsRepository,
    private val walletRepository: WalletRepository,
    private val mnemonicStorage: MemoryStorage,
) {

    suspend operator fun invoke(hdWallet: HDWallet) {
        val getAddressByCoinType: (CoinType) -> String = { coinType ->
            hdWallet.getAddressForCoin(coinType)
        }
        mnemonicStorage.mnemonic = hdWallet.mnemonic()

        val request = assetsRepository.getAssetList().map {  asset ->
            val coinType = CoinType.createFromValue(asset.network)
            val address = getAddressByCoinType(coinType)

            CreateWalletRequest(
                address = address, coinType = coinType, assets = asset
            )
        }

        walletRepository.createWallets(request)
    }
}