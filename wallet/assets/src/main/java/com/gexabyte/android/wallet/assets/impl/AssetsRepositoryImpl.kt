package com.gexabyte.android.wallet.assets.impl

import android.content.res.AssetManager
import android.util.Log
import com.gexabyte.android.wallet.assets.AssetsRepository
import com.gexabyte.android.wallet.assets.CryptoAsset
import com.gexabyte.android.wallet.assets.database.dao.AssetsVersionDao
import com.gexabyte.android.wallet.assets.database.dao.CryptoAssetsDao
import com.gexabyte.android.wallet.assets.database.entity.AssetsVersionEntity
import com.gexabyte.android.wallet.assets.mapper.toCryptoAssetsEntity
import com.gexabyte.android.wallet.assets.mapper.toModel
import com.gexabyte.android.wallet.assets.models.StaticAssetsModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class AssetsRepositoryImpl(
    private val assetsManager: AssetManager,
    private val versionDao: AssetsVersionDao,
    private val assetsDao: CryptoAssetsDao,
    externalScope: CoroutineScope,
): AssetsRepository {
    private val gson by lazy {
        GsonBuilder().setLenient().create()
    }

    init {
        externalScope.launch {
            val staticAssets = with(assetsManager.reedAssetsFile(COINS_JSON_FILE_NAME)) {
                gson.fromJson(this, StaticAssetsModel::class.java)
            }
            val storeVersion = versionDao.getCurrentVersion()?.version ?: 0L

            if(staticAssets.version.toLong() == storeVersion) {
                return@launch
            }

            assetsDao.dropAll()
            staticAssets.assets.forEach { asset ->
                assetsDao.insertNewAsset(asset = asset.toCryptoAssetsEntity())
            }

            versionDao.updateVersion(AssetsVersionEntity(version = staticAssets.version.toLong()))
        }
    }

    override suspend fun getAsset(symbol: String, coinType: Int?): CryptoAsset {
        val asset = if(coinType != null) {
            assetsDao.getAssetBySymbolAndCoinType(symbol, coinType)
        } else {
            assetsDao.getAssetBySymbol(symbol)
        }

        Log.d("DEBUG", "$symbol, $coinType")
        return requireNotNull(asset).toModel()
    }

    override suspend fun getAssetList(): List<CryptoAsset> {
       return assetsDao.getAssetList().map { it.toModel() }
    }

    companion object {
        private const val COINS_JSON_FILE_NAME = "coins.json"
    }
}

fun AssetManager.reedAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }