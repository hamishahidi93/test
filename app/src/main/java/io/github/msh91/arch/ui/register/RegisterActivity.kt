package io.github.msh91.arch.ui.register

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.Configuration
import io.github.msh91.arch.R
import io.github.msh91.arch.databinding.ActivityRegisterBinding
import io.github.msh91.arch.ui.base.BaseActivity
import io.github.msh91.arch.ui.home.HomeActivity


class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(), Configuration.Provider  {

    override val layoutId: Int = R.layout.activity_register
    override val viewModel: RegisterViewModel by getLazyViewModel()

    override fun onViewInitialized(binding: ActivityRegisterBinding) {
        super.onViewInitialized(binding)

        binding.viewModel = viewModel

        binding.btnRegister.setOnClickListener {
            viewModel.register(binding.inputName.text.toString(), binding.inputCode.text.toString())
        }

        viewModel.jwtToken?.observe(this, Observer {
            Log.d(TAG, it)
            startActivity(Intent(this, HomeActivity::class.java))
        })

        viewModel.error?.observe(this, Observer {
            showToast(it::class.simpleName)
        })

        viewModel.localError?.observe(this, Observer {
            showToast(it)
        })

    }


    companion object {
        private const val TAG = "RegisterActivityTag"
    }

    override fun getWorkManagerConfiguration(): Configuration {
        TODO("Not yet implemented")
    }

}
