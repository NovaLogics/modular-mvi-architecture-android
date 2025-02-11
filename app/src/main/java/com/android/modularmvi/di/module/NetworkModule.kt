package com.android.modularmvi.di.module

import android.content.Context
import com.android.modularmvi.BuildConfig
import com.android.modularmvi.data.remote.QuoteApiService
import com.android.modularmvi.di.component.getUnsafeSSLSocketFactory
import com.android.modularmvi.di.component.getUnsafeTrustManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB

    /**
     * Provides an instance of QuoteApiService.
     */
    @Provides
    @Singleton
    fun provideQuoteApiService(retrofit: Retrofit): QuoteApiService =
        retrofit.create(QuoteApiService::class.java)

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE)


    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(logger)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .sslSocketFactory(getUnsafeSSLSocketFactory(), getUnsafeTrustManager())
            .hostnameVerifier { _, _ -> true }
            .cache(cache)
            .build()
    }


    /**
     * Provides a Retrofit instance for network calls.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
