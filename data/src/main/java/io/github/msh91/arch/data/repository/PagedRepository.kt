package io.github.msh91.arch.data.repository

import androidx.paging.DataSource
import io.github.msh91.arch.data.di.qualifier.Concrete
import io.github.msh91.arch.data.repository.inspectors.InspectorsRepository
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.data.source.remote.InspectorsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PagedRepository @Inject constructor(
    private val inspectorsRepository: InspectorsRepository
    ){
//    val serverList =  inspectorsRepository.getServerModels(pagingCount = params.requestedLoadSize)

}
