package com.gexabyte.android.wallet.rates.data

import com.gexabyte.android.wallet.rates.data.response.CmcListingLatestResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CmcService {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCmcListingData(
        @Query("convert") convert: String = "USD",
        @Query("limit") limit: Int = 200,
    ): NetworkResponse<CmcListingLatestResponse, Any>
}
