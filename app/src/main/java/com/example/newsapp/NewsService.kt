package com.example.newsapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://newsapi.org/v2/top-headlines?country=in&apiKey=1625f9f25cf84493969d51e28bdcea16

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "1625f9f25cf84493969d51e28bdcea16"

interface NewsInterface{

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country:String, @Query("page") page:Int):Call<News>
}

object NewsService{
    val newsInstances: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstances = retrofit.create(NewsInterface::class.java)
         }
}