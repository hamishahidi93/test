package io.github.msh91.arch.ui.register

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import io.github.msh91.arch.R
import io.github.msh91.arch.databinding.ActivityRegisterBinding
import io.github.msh91.arch.ui.base.BaseActivity
import io.github.msh91.arch.ui.home.HomeActivity


class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>() {

    override val layoutId: Int = R.layout.activity_register
    override val viewModel: RegisterViewModel by getLazyViewModel()

    override fun onViewInitialized(binding: ActivityRegisterBinding) {
        super.onViewInitialized(binding)

        binding.viewModel = viewModel

        binding.btnRegister.setOnClickListener {

            if (viewModel.isCodeAndNameValid(binding.inputName.text.toString(), binding.inputCode.text.toString())) {

                viewModel.register(binding.inputName.text.toString(), binding.inputCode.text.toString())

            } else {
                Toast.makeText(this, getString(R.string.invalid_code_name), Toast.LENGTH_SHORT).show();
            }
        }

        viewModel.jwtToken?.observe(this, Observer {
            Log.d(TAG, viewModel.jwtToken!!.value.toString())
            startActivity(Intent(this, HomeActivity::class.java))
        })

        viewModel.error?.observe(this, Observer {

            Toast.makeText(this, viewModel.error?.value.toString(), Toast.LENGTH_SHORT).show();

        })

    }

    companion object {
        private const val TAG = "RegisterActivityTag"
    }

}
