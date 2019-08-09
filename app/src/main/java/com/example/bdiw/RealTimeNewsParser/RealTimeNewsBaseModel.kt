package com.example.bdiw.RealTimeNewsParser

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RealTimeNewsBaseModel (
    @SerializedName("status") val status : String,
    @SerializedName("copyright") val copyright : String,
    @SerializedName("num_results") val num_results : Int,
    @SerializedName("results") val results : List<Results>
):Serializable