package com.example.pruebatecnicaasd.core.network

import com.example.pruebatecnicaasd.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class PeliculasInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "es-MX")
            .build()

        val requestBuilder = original.newBuilder().url(newUrl)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}