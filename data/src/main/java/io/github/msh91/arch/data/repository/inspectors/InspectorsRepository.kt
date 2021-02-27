package io.github.msh91.arch.data.repository.inspectors

import androidx.paging.PagingSource
import com.stealthcopter.networktools.PortScan
import io.github.msh91.arch.data.di.qualifier.Concrete
import io.github.msh91.arch.data.mapper.ErrorMapper
import io.github.msh91.arch.data.model.inquiry.InquiryServerDto
import io.github.msh91.arch.data.model.inquiry.InspectedServerRequest
import io.github.msh91.arch.data.model.inquiry.IsPNameResponseDto
import io.github.msh91.arch.data.repository.BaseRepository
import io.github.msh91.arch.data.source.db.ServerDAO
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.data.source.remote.InspectorsDataSource
import java.util.*
import javax.inject.Inject

class InspectorsRepository @Inject constructor(
    errorMapper: ErrorMapper,
    @Concrete private val inspectorsDataSource: InspectorsDataSource,
    private val serverDAO: ServerDAO
) : BaseRepository(errorMapper) {

    suspend fun getIsPName(): IsPNameResponseDto {
        return inspectorsDataSource.getIspName("http://ip-api.com/json/")
    }

    suspend fun sendInspectedServer(inspectedServerRequest: InspectedServerRequest) {
        val response =  inspectorsDataSource.sendInspectedServer(inspectedServerRequest)
        insertServerToDb(
            response.id,
            response.hash_key,
            response.ip,
            response.received_isp,
            inspectedServerRequest.isActive
        )
    }

    suspend fun getInquiryServers(): List<InquiryServerDto> {
        return inspectorsDataSource.getInquiryServer()
    }


    fun scanIpPorts(inquiryServerDto: InquiryServerDto): List<Int> {
        return PortScan
            .onAddress(inquiryServerDto.ip)
            .setMethodUDP()
            .setPorts(inquiryServerDto.ports.map { it.toInt() } as ArrayList<Int>)
            .doScan() ?: emptyList()
    }


    private fun insertServerToDb(serverId: String, hashKey: String, ip: String,receivedIsp: String, isActive: String) {
        val serverModel = ServerModel(serverId, hashKey, ip, isActive,receivedIsp, Date())
        serverDAO.insertServerToDb(serverModel)
    }


    fun getServerModels(): PagingSource<Int, ServerModel> {
        return serverDAO.getServerModels()
    }


}
