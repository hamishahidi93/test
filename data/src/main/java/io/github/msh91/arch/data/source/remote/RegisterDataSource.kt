package io.github.msh91.arch.data.source.remote

import io.github.msh91.arch.data.source.remote.model.register.RegisterResponseDto
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterDataSource {

    @FormUrlEncoded
    @POST("api/v1/inspectors/register/")
    suspend fun register(
        @FieldMap a: HashMap<String, String>
    ): RegisterResponseDto

}
