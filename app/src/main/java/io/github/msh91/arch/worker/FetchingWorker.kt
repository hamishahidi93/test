package io.github.msh91.arch.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import javax.inject.Inject
import javax.inject.Provider

class FetchingWorker(
    application: Context,
    workerParameters: WorkerParameters,
    private val inspectorsRepository: InspectorsRepository
) : CoroutineWorker(application, workerParameters) {


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

    override suspend fun doWork(): Result {
        Log.d("FETCH_WORKER_TAG" ,"fetch :))" )
        inspectorsRepository.getInquiryServer()
        return Result.success()
    }

}
