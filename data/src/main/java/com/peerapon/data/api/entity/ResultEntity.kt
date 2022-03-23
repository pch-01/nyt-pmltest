package com.peerapon.data.api.entity

import com.google.gson.annotations.SerializedName

data class ResultEntity<T>(
    @SerializedName("results") val list: List<T>,
    val status: String?,
)