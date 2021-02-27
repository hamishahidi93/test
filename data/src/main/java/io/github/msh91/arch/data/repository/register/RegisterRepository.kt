package io.github.msh91.arch.data.repository.register

import arrow.core.Either
import io.github.msh91.arch.data.di.qualifier.Concrete
import io.github.msh91.arch.data.mapper.ErrorMapper
import io.github.msh91.arch.data.model.Error
import io.github.msh91.arch.data.model.register.RegisterRequest
import io.github.msh91.arch.data.repository.BaseRepository
import io.github.msh91.arch.data.source.remote.RegisterDataSource
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    errorMapper: ErrorMapper,
    @Concrete private val registerDataSource: RegisterDataSource
) : BaseRepository(errorMapper) {

    suspend fun register(
        name: String ,
        code: String
    ): Either<Error, String> {
        val body = RegisterRequest(name , code)
        return safeApiCall { registerDataSource.register(body) }
            .map { it.data }
    }


}
