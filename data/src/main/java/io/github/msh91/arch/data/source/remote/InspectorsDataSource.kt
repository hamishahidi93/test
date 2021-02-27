package io.github.msh91.arch.data.source.remote

import io.github.msh91.arch.data.model.inquiry.InquiryServerDto
import io.github.msh91.arch.data.model.inquiry.InspectedServerRequest
import io.github.msh91.arch.data.model.inquiry.InspectedServerResponseDto
import io.github.msh91.arch.data.model.inquiry.IsPNameResponseDto
import retrofit2.Call
import retrofit2.http.*

interface InspectorsDataSource {

    @GET("api/v1/inspectors/inquiry-server")
    suspend fun getInquiryServer(): List<InquiryServerDto>

    @POST("api/v1/inspectors/inspected-server/")
    suspend fun sendInspectedServer(
        @Body body:  InspectedServerRequest
    ): InspectedServerResponseDto

    @GET
    suspend fun getIspName(@Url url: String): IsPNameResponseDto



}
