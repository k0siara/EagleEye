package com.patrykkosieradzki.eagleeye.network.di

import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<AppConfiguration>().eagleEyeApiBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

//    single<CatApiService> {
//        get<Retrofit>().create(CatApiService::class.java)
//    }
//
//    single<CatRepository> {
//        CatApiRepository(get())
//    }
}