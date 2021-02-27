package io.github.msh91.arch.data.model.inquiry

import com.google.gson.annotations.SerializedName

data class InquiryServerDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("hash_key")
    val hashKey: String,
    @SerializedName("ip")
    val ip: String,
    @SerializedName("ports")
    val ports: ArrayList<String>
)
