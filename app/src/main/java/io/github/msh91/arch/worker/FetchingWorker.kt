package io.github.msh91.arch.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import io.github.msh91.arch.data.model.inquiry.InquiryServerDto
import io.github.msh91.arch.data.model.inquiry.InspectedServerRequest
import io.github.msh91.arch.data.model.inquiry.IsPNameResponseDto
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider


class FetchingWorker(
    application: Context,
    workerParameters: WorkerParameters,
    private val inspectorsRepository: InspectorsRepository
) : CoroutineWorker(application, workerParameters) {
    companion object {
        private const val TAG = "FetchingWorker"
    }
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val ispName = try {
                    inspectorsRepository.getIsPName().isp
                } catch (ex: Exception) {
                    ""
                }
                val inquiryServers = inspectorsRepository.getInquiryServers()
                inquiryServers.forEach { scanIpPorts(it, ispName) }
                Result.success()
            } catch (ex: Exception) {
                Result.failure()
            }
        }
    }

    private suspend fun scanIpPorts(inquiryServerDto: InquiryServerDto, ispName: String) {
        try {
            val openPorts = inspectorsRepository.scanIpPorts(inquiryServerDto)
            val status = if (openPorts.isNotEmpty()) "true" else "false"
            val body = InspectedServerRequest(
                inquiryServerDto.id,
                inquiryServerDto.hashKey,
                inquiryServerDto.ip,
                ispName,
                status
            )
            inspectorsRepository.sendInspectedServer(body)
        } catch (ex: Exception) {
            Log.d(TAG, "doWork: scan ${inquiryServerDto.ip} failed")
        }
    }


    class Factory @Inject constructor(
        private val foo: Provider<InspectorsRepository>
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return FetchingWorker(
                appContext,
                params,
                foo.get()
            )
        }
    }


}
