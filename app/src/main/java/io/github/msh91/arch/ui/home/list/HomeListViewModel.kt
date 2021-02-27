package io.github.msh91.arch.ui.home.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.ui.base.BaseViewModel
import io.github.msh91.arch.util.livedata.SingleEventLiveData
import io.github.msh91.arch.worker.FetchingWorker
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HomeListViewModel @Inject constructor(
    private val inspectorsRepository: InspectorsRepository
) : BaseViewModel() {


    var serverDetail: LiveData<ServerModel>? = null

    val allServersFromDb = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 60,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        inspectorsRepository.getServerModels()
    }.flow

    init {
        serverDetail = SingleEventLiveData()

    }

    companion object {
        private const val TAG = "HomeListViewModel"

    }
}
