package io.github.msh91.arch.data.source.remote

import io.github.msh91.arch.data.model.register.RegisterRequest
import io.github.msh91.arch.data.source.remote.model.register.RegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterDataSource {

    @POST("api/v1/inspectors/register/")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterResponseDto

}
