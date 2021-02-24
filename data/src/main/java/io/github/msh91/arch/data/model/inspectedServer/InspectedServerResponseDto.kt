package io.github.msh91.arch.data.model.inspectedServer

import com.google.gson.annotations.SerializedName

data class InspectedServerResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("received_isp")
    var received_isp: String ,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("hash_key")
    val hash_key: String,
    @SerializedName("is_active")
    val is_active: Boolean,
    @SerializedName("server")
    val server: String,
    @SerializedName("detected_isp")
    val detected_isp: String
)
