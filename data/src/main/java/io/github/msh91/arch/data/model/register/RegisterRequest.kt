package io.github.msh91.arch.data.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("code")
    val code: String
)
