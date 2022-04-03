package com.gexabyte.android.wallet.rates.data

import com.gexabyte.android.wallet.rates.CoinMarketData
import com.gexabyte.android.wallet.rates.RatesRepository
import com.gexabyte.android.wallet.rates.database.dao.CmcDao
import com.gexabyte.android.wallet.rates.mapper.toCoinMarketDTO
import com.gexabyte.android.wallet.rates.mapper.toCoinMarketEntity
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class RatesRepositoryImpl(
    private val cmcService: CmcService,
    private val cmcDao: CmcDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RatesRepository {

    override fun flowOnAllCmcPrice(): Flow<List<CoinMarketData>> =
        cmcDao
            .observeCmc()
            .map { list ->
                list.map {
                    it.toCoinMarketDTO()
                }
            }.flowOn(ioDispatcher)

    override fun flowOnCmcPrice(symbol: String): Flow<CoinMarketData> =
        cmcDao.observeCmcBySymbol(symbol)
            .map {
                it.toCoinMarketDTO()
            }
            .flowOn(ioDispatcher)

    override suspend fun getCmcPrice(symbol: String): CoinMarketData? {
        return withContext(ioDispatcher) {
            cmcDao.getCmcBySymbol(symbol)?.toCoinMarketDTO()
        }
    }

    override suspend fun updateAllCmcData(convertCurrency: String): Result<Unit> {
        return withContext(ioDispatcher) {
            val entities =
                when (val result = cmcService.getCmcListingData()) {
                    is NetworkResponse.Success -> {
                        result.body.data.map { listingData ->
                            listingData.toCoinMarketEntity(convertCurrency)
                        }
                    }
                    else -> null
                }

            if (entities != null) {
                cmcDao.insertAll(entities)
            }

            return@withContext Result.success(Unit)
        }
    }
}