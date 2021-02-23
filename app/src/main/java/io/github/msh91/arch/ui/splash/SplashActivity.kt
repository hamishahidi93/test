package io.github.msh91.arch.ui.splash

import android.content.Intent
import android.os.Handler
import androidx.lifecycle.Observer
import io.github.msh91.arch.R
import io.github.msh91.arch.databinding.ActivitySplashBinding
import io.github.msh91.arch.ui.base.BaseActivity
import io.github.msh91.arch.ui.home.HomeActivity
import io.github.msh91.arch.ui.register.RegisterActivity


class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val layoutId: Int = R.layout.activity_splash
    override val viewModel: SplashViewModel by getLazyViewModel()

    override fun onViewInitialized(binding: ActivitySplashBinding) {
        super.onViewInitialized(binding)

        binding.viewModel = viewModel
        viewModel.checkIsAuthenticated()



        viewModel.isAuthenticated?.observe(this, Observer {isAuthenticate ->
            val handler = Handler()
            handler.postDelayed({
                if (isAuthenticate){
                    startActivity(Intent(this, HomeActivity::class.java))
                }else{
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
                finish()

            }, 2000)

        })

    }

    companion object {
        private const val TAG = "RegisterActivityTag"
    }

}
