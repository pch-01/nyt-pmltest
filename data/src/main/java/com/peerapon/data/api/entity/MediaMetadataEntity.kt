package com.peerapon.data.api.entity

import com.google.gson.annotations.SerializedName

data class MediaMetadataEntity(
    @SerializedName("url") val mediaUrl: String
)