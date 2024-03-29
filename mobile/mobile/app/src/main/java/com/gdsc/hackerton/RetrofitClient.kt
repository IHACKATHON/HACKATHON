package com.gdsc.hackerton

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient{
   companion object {
       private const val BASE_URL = "http://10.0.0.2:8080"
//       private const val BASE_URL = "http://10.0.2.2:8080" 일수도 있음
       private var retrofit: ApiService? = null

       val loggingInterceptor = HttpLoggingInterceptor()

       val client = OkHttpClient.Builder()
               .addInterceptor(loggingInterceptor)
           .build()

       fun init(){
           loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
       }

       fun getClient(): ApiService {
           if (retrofit == null) {
               init()
               retrofit = Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .client(client)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
                   .create(ApiService::class.java)
           }
           return retrofit!!
       }
   }
}


