package io.github.msh91.arch.data.repository.inspectors

import androidx.paging.PagingSource
import io.github.msh91.arch.data.di.qualifier.Concrete
import io.github.msh91.arch.data.mapper.ErrorMapper
import io.github.msh91.arch.data.model.inquiryServer.InquiryServerDto
import io.github.msh91.arch.data.repository.BaseRepository
import io.github.msh91.arch.data.source.db.ServerDAO
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.data.source.remote.InspectorsDataSource
import io.github.msh91.arch.data.model.inspectedServer.InspectedServerResponseDto
import io.github.msh91.arch.data.model.isPName.IsPNameResponseDto
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class InspectorsRepository @Inject constructor(
    errorMapper: ErrorMapper,
    @Concrete private val inspectorsDataSource: InspectorsDataSource,
    private val serverDAO: ServerDAO
) : BaseRepository(errorMapper) {

    fun sendInspectedServer(
        params: HashMap<String, String>
    ): Observable<InspectedServerResponseDto> {
        return inspectorsDataSource.sendInspectedServer(params)
    }


    fun getInquiryServer(): Observable<List<InquiryServerDto>> {
        return inspectorsDataSource.getInquiryServer()
    }

    fun getIsPName(): Observable<IsPNameResponseDto> {
        return inspectorsDataSource.getIspName("http://ip-api.com/json/")
    }


    fun insertServerToDb(serverId: String, hashKey: String, ip: String
                         ,receivedIsp: String, isActive: String) {
        val serverModel = ServerModel(serverId, hashKey, ip, isActive,receivedIsp, Date())
        return serverDAO.insertServerToDb(serverModel)
    }


    fun getServerModels(): PagingSource<Int, ServerModel> {
        return serverDAO.getServerModels()
    }


}
