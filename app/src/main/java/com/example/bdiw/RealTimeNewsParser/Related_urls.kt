package com.example.bdiw.RealTimeNewsParser

import com.google.gson.annotations.SerializedName

data class Related_urls (
    @SerializedName("suggested_link_text") val suggested_link_text : String,
    @SerializedName("url") val url : String
)