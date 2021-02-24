package io.github.msh91.arch.data.model.inquiryServer

import com.google.gson.annotations.SerializedName

data class InquiryServerDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("hash_key")
    val hash_key: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("ports")
    val ports: ArrayList<String>,
    @SerializedName("connection_status")
    var connection_status: String

)
