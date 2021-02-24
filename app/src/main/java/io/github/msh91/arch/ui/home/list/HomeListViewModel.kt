package io.github.msh91.arch.ui.home.list

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.ui.base.BaseViewModel
import io.github.msh91.arch.util.livedata.SingleEventLiveData
import javax.inject.Inject


class HomeListViewModel @Inject constructor(
    private val inspectorsRepository: InspectorsRepository
) : BaseViewModel() {

    var isLoading: SingleEventLiveData<Boolean>? = null

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
        isLoading = SingleEventLiveData()
        serverDetail = SingleEventLiveData()

    }

    companion object {
        private const val TAG = "HomeListViewModel"

    }
}
