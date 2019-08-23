package com.example.bdiw.news.NewsFromApi

import com.example.bdiw.news.NewsJsonParserModel.NewsJsonBaseModel
import com.example.bdiw.news.RealTimeNewsParser.RealTimeNewsBaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesRequestInterface {

    @GET("all.json")
    abstract fun getNews(@Query("api-key") key:String):Call<RealTimeNewsBaseModel>

    @GET("7.json")
    abstract fun getArchiveNews(@Query("api-key") key:String):Call<NewsJsonBaseModel>


}