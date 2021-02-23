package io.github.msh91.arch.data.source.remote.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
    @SerializedName("token") val data: String
)
