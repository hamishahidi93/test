package io.github.msh91.arch.data.paging


//class PagingDataSource(val repository: InspectorsRepository) : PagingSource<Int,ServerModel >(){
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ServerModel> {
//        val pageIndex = params.key?:0
//        val response = repository.getServerModels(20,pageIndex)
//        return LoadResult.Page(
//            data = response,
//            prevKey =
//        )
//    }
//
//}
