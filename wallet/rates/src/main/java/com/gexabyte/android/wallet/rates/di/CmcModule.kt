package com.gexabyte.android.wallet.rates.di

import android.content.Context
import androidx.room.Room
import com.gexabyte.android.wallet.rates.RatesRepository
import com.gexabyte.android.wallet.rates.data.CmcService
import com.gexabyte.android.wallet.rates.data.RatesRepositoryImpl
import com.gexabyte.android.wallet.rates.database.RatesDatabase
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val COIN_MARKET_API_HOST = "https://pro-api.coinmarketcap.com"

val cmcModule = module {
    single { provideRatesDateBase(get()) }
    single { get<RatesDatabase>().cmcDao() }

    single { get<Retrofit>(named("cmc")).create(CmcService::class.java) }
    single<RatesRepository> { RatesRepositoryImpl(get(), get()) }
    single(named("cmc")) { getCoinMarketCapNetworkClient() }
    single(named("cmc")) {
        provideRetrofit(
            get(named("cmc")),
        )
    }
}

private fun provideRatesDateBase(context: Context): RatesDatabase {
    return Room.databaseBuilder(context, RatesDatabase::class.java, "rates.db")
        .fallbackToDestructiveMigration()
        .build()
}

private fun getCoinMarketCapNetworkClient(): OkHttpClient {
    val httpBuilder = OkHttpClient.Builder()

        .addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader(
                    "X-CMC_PRO_API_KEY",
                    "69bed206-1375-4b50-bdac-94eef2bd428f"
                )
            chain.proceed(newRequest.build())
        }
        .callTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)

    return httpBuilder.build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(COIN_MARKET_API_HOST)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(okHttpClient)
        .build()
