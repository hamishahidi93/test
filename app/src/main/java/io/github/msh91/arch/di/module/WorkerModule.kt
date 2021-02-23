package io.github.msh91.arch.di.module

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.msh91.arch.data.di.qualifier.WorkerKey
import io.github.msh91.arch.worker.ChildWorkerFactory
import io.github.msh91.arch.worker.FetchingWorker


@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(FetchingWorker::class)
    fun bindFetchingWorkerWorker(factory: FetchingWorker.Factory): ChildWorkerFactory
}
