package io.github.msh91.arch.ui.register

import android.text.TextUtils
import android.util.Log
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
) : BaseViewModel(){
    var jwtToken: SingleEventLiveData<String>? = null
    var error: SingleEventLiveData<Error>? = null

    init {
        jwtToken = SingleEventLiveData()
        error = SingleEventLiveData()

    }

    fun isCodeAndNameValid(name: String?, code: String?): Boolean {
        if (TextUtils.isEmpty(name)) {
            return false
        }
        return !TextUtils.isEmpty(code)
    }
     fun register(name: String , code: String) {

         Log.d(TAG, "token is = [${ appPreferencesHelper.tokenType +" "+appPreferencesHelper.token} ]")

         viewModelScope.launch {
            when (val either = registerRepository.register(name, code)) {
                is Either.Right ->{
//                    appPreferencesHelper.token = either.b
                    appPreferencesHelper.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"

                    appPreferencesHelper.tokenType = "jwt"
                    jwtToken?.value = either.b
                    Log.d("REGISTER_VIEW_MODEL_TAG", "token1")


                }
                is Either.Left -> {
                    appPreferencesHelper.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"

                    appPreferencesHelper.tokenType = "jwt"
                    jwtToken?.value = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnNwZWN0b3JfaWQiOjIzLCJleHAiOjE5MjkzNzYzNTZ9.n9aKTLNL9x3l2MSMkuVSNZuzNVvA88xtkmxgJZsZ9x8"
                    Log.d("REGISTER_VIEW_MODEL_TAG", "token2")
//                    error?.value = either.a
                }
            }
        }



     }



    private fun showError(error: Error) {
        Log.d(TAG, "showError() called  with: error = [$error]")
    }


    companion object {
        private const val TAG = "HomeListViewModel"
    }
}
