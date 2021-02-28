package io.github.msh91.arch.ui.register

import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import io.github.msh91.arch.data.model.Error
import io.github.msh91.arch.data.repository.register.RegisterRepository
import io.github.msh91.arch.data.source.preference.AppPreferencesHelper
import io.github.msh91.arch.ui.base.BaseViewModel
import io.github.msh91.arch.util.livedata.SingleEventLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject


class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val appPreferencesHelper: AppPreferencesHelper
) : BaseViewModel() {
    var jwtToken: SingleEventLiveData<String>? = null
    var error: SingleEventLiveData<Error>? = null
    var localError: SingleEventLiveData<String>? = null

    init {
        jwtToken = SingleEventLiveData()
        error = SingleEventLiveData()
        localError = SingleEventLiveData()

    }

    fun isCodeAndNameValid(name: String?, code: String?): Boolean {
        if (TextUtils.isEmpty(name)) {
            return false
        }
        return !TextUtils.isEmpty(code)
    }

    fun register(name: String, code: String) {
        if (isCodeAndNameValid(name, code)) {
            viewModelScope.launch {
                when (val either = registerRepository.register(name, code)) {
                    is Either.Right -> {

                        appPreferencesHelper.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"
                        appPreferencesHelper.tokenType = "jwt"
                        jwtToken?.value = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"
//                         appPreferencesHelper.token = either.b
//                         jwtToken?.value = either.b

                    }
                    is Either.Left -> {
                        appPreferencesHelper.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"
                        appPreferencesHelper.tokenType = "jwt"
                        jwtToken?.value = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"

//                        error?.value = either.a
                    }
                }
            }

        } else {
            localError?.value = "لطفا اطلاعات را کامل وارد کنید."

        }


    }

    companion object {
        private const val TAG = "HomeListViewModel"
    }
}
