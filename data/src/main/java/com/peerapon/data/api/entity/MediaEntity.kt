package com.peerapon.data.api.entity

import com.google.gson.annotations.SerializedName

data class MediaEntity(
    val type: String?,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadataEntity>
)