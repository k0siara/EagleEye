package com.patrykkosieradzki.eagleeye.network.di

import com.patrykkosieradzki.eagleeye.domain.AppConfiguration
import com.patrykkosieradzki.eagleeye.domain.repositories.CameraRepository
import com.patrykkosieradzki.eagleeye.domain.repositories.LoginRepository
import com.patrykkosieradzki.eagleeye.network.repositories.CameraApiRepository
import com.patrykkosieradzki.eagleeye.network.repositories.LoginApiRepository
import com.patrykkosieradzki.eagleeye.network.services.EagleEyeCameraService
import com.patrykkosieradzki.eagleeye.network.services.EagleEyeLoginService
import com.patrykkosieradzki.eagleeye.network.utils.ErrorHandlingCallAdapterFactory
import com.patrykkosieradzki.eagleeye.network.utils.NetworkHandler
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

    single<OkHttpClient> {
        CustomOkHttpClientFactory(
            appConfiguration = get()
        ).createOkHttpClient()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<AppConfiguration>().eagleEyeApiBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory())
            .client(get())
            .build()
    }

    single {
        NetworkHandler(
            clearSessionUseCase = get(),
            appConfiguration = get()
        )
    }

    single<EagleEyeLoginService> {
        get<Retrofit>().create(EagleEyeLoginService::class.java)
    }

    single<EagleEyeCameraService> {
        get<Retrofit>().create(EagleEyeCameraService::class.java)
    }

    single<LoginRepository> {
        LoginApiRepository(
            eagleEyeLoginService = get(),
            networkHandler = get()
        )
    }

    single<CameraRepository> {
        CameraApiRepository(
            cameraService = get(),
            networkHandler = get()
        )
    }
}