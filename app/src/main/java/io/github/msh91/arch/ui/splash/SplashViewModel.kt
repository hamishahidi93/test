package io.github.msh91.arch.ui.splash

import android.util.Log
import io.github.msh91.arch.data.source.preference.AppPreferencesHelper
import io.github.msh91.arch.ui.base.BaseViewModel
import io.github.msh91.arch.util.livedata.SingleEventLiveData
import javax.inject.Inject


class SplashViewModel @Inject constructor(
    private val appPreferencesHelper: AppPreferencesHelper
) : BaseViewModel(){
    var isAuthenticated: SingleEventLiveData<Boolean>? = null

    init {
        isAuthenticated = SingleEventLiveData()

    }

     fun checkIsAuthenticated() {

         isAuthenticated?.value = appPreferencesHelper.token != ""
         Log.d(TAG, "token is = [${ appPreferencesHelper.tokenType +" "+appPreferencesHelper.token} ]")

     }


    companion object {
        private const val TAG = "HomeListViewModel"
    }
}
