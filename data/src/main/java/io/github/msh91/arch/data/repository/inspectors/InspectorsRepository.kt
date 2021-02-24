package io.github.msh91.arch.data.repository.inspectors

import androidx.annotation.WorkerThread
import androidx.paging.PagingSource
import arrow.core.Either
import io.github.msh91.arch.data.di.qualifier.Concrete
import io.github.msh91.arch.data.mapper.ErrorMapper
import io.github.msh91.arch.data.model.Error
import io.github.msh91.arch.data.model.inquiryServer.InquiryServerDto
import io.github.msh91.arch.data.repository.BaseRepository
import io.github.msh91.arch.data.source.db.ServerDAO
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.data.source.remote.InspectorsDataSource
import io.github.msh91.arch.data.source.remote.model.inspectedServer.InspectedServerResponseDto
import io.github.msh91.arch.data.source.remote.model.isPName.IsPNameResponseDto
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class InspectorsRepository @Inject constructor(
    errorMapper: ErrorMapper,
    @Concrete private val inspectorsDataSource: InspectorsDataSource,
    private val serverDAO: ServerDAO
) : BaseRepository(errorMapper)  {

    suspend fun sendInspectedServer(
        params: HashMap<String, String>
    ): Either<Error, InspectedServerResponseDto> {

        return safeApiCall { inspectorsDataSource.sendInspectedServer(params) }
            .map { it }
    }


    suspend fun getInquiryServer(): Either<Error, List<InquiryServerDto>> {
        return safeApiCall { inspectorsDataSource.getInquiryServer() }
            .map { it }
    }

    suspend fun getIsPName(): Either<Error, IsPNameResponseDto> {
        return safeApiCall { inspectorsDataSource.getIspName("http://ip-api.com/json/") }
            .map { it }
    }

    @WorkerThread
    suspend   fun insertServer( serverId: String , hashKey: String,ip: String,ports: List<String>
                      ,isActive: String) {
        withContext(IO) {
                val serverModel = ServerModel(serverId, hashKey ,ip,ports,"",  isActive, Date())
                serverDAO.insertServer(serverModel)
        }

    }

    @WorkerThread
    suspend fun updateServer(isActive: String,serverId: String) {
        withContext(IO) {
            serverDAO.updateServer(isActive, serverId)
        }

    }


    @WorkerThread
    suspend  fun deleteServer(serverId: String?) {
        withContext(IO) {
            serverDAO.deleteServer(serverId)

        }

    }
    @WorkerThread
    suspend  fun deleteAll() {
        withContext(IO) {
            serverDAO.deleteAll()

        }
    }

    @WorkerThread
    suspend  fun getServerDetail(serverId: String) {
        withContext(IO) {
            serverDAO.getServerDetail(serverId)

        }
    }


    fun getServerModels(): PagingSource<Int, ServerModel> {
        return serverDAO.getServerModels()
    }



}
