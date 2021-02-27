package io.github.msh91.arch.data.model.inquiry

import com.google.gson.annotations.SerializedName

data class InspectedServerRequest(
    @SerializedName("server")
    val server: String,
    @SerializedName("hash_key")
    val hashKey: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("received_isp")
    var receivedIsp: String ,
    @SerializedName("is_active")
    val isActive: String

)
