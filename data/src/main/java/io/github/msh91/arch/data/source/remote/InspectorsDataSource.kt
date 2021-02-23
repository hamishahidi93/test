package io.github.msh91.arch.data.source.remote

import io.github.msh91.arch.data.model.inquiryServer.InquiryServerDto
import io.github.msh91.arch.data.source.remote.model.inspectedServer.InspectedServerResponseDto
import io.github.msh91.arch.data.source.remote.model.isPName.IsPNameResponseDto
import retrofit2.http.*

interface InspectorsDataSource {

    @GET("api/v1/inspectors/inquiry-server")
    suspend fun getInquiryServer(): List<InquiryServerDto>

    @FormUrlEncoded
    @POST("api/v1/inspectors/inspected-server/")
    suspend fun sendInspectedServer(
        @FieldMap a:  HashMap<String, String>
    ): InspectedServerResponseDto

    @GET
    suspend fun getIspName(@Url url: String): IsPNameResponseDto



}
