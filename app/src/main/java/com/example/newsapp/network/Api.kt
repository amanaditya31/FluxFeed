package com.example.newsapp.network

import androidx.compose.ui.focus.FocusDirection.Companion.In
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Api {
    const val API_KEY="11e71d756e1f4425a86ebf51645f7d78"
    private val BASE_URL= "https://newsapi.org/v2/"


    private val moshi= Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val logging=HttpLoggingInterceptor()

    val httpClient=OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{
                chain ->
                val builder=chain.request()
                    .newBuilder()
                builder.header("X-Api-Key", API_KEY)
                return@Interceptor chain.proceed(builder.build())
            }
        )
        logging.level=HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    private val retrofit= Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: NewsService by lazy{
        retrofit.create(NewsService:: class.java)
    }


}