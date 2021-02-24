package io.github.msh91.arch.data.source.remote

import io.github.msh91.arch.data.model.inquiryServer.InquiryServerDto
import io.github.msh91.arch.data.model.inspectedServer.InspectedServerResponseDto
import io.github.msh91.arch.data.model.isPName.IsPNameResponseDto
import io.reactivex.Observable
import retrofit2.http.*

interface InspectorsDataSource {

    @GET("api/v1/inspectors/inquiry-server")
    fun getInquiryServer(): Observable<List<InquiryServerDto>>

    @FormUrlEncoded
    @POST("api/v1/inspectors/inspected-server/")
    fun sendInspectedServer(
        @FieldMap a:  HashMap<String, String>
    ): Observable<InspectedServerResponseDto>

    @GET
    fun getIspName(@Url url: String): Observable<IsPNameResponseDto>



}
