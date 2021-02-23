package io.github.msh91.arch.ui.home.list

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import io.github.msh91.arch.R
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.databinding.FragmentHomeListBinding
import io.github.msh91.arch.databinding.ItemServerBinding
import io.github.msh91.arch.ui.base.BaseFragment
import io.github.msh91.arch.ui.base.ViewModelScope
import io.github.msh91.arch.ui.base.adapter.ServerAdapter
import io.github.msh91.arch.ui.base.adapter.SingleLayoutAdapter
import io.github.msh91.arch.util.extension.observeSafe

class HomeListFragment : BaseFragment<HomeListViewModel, FragmentHomeListBinding>() {

    override val viewModel: HomeListViewModel by getLazyViewModel(ViewModelScope.ACTIVITY)
    override val layoutId: Int = R.layout.fragment_home_list

    override fun onViewInitialized(binding: FragmentHomeListBinding) {
        super.onViewInitialized(binding)
        binding.viewModel = viewModel
        binding.adapter = ServerAdapter()


        viewModel.allServers.observeSafe(viewLifecycleOwner){serverModels ->
//            binding.adapter?.swapItems(serverModels)

        }

        viewModel.error?.observe(this, Observer {
//            Toast.makeText(context, viewModel.error?.value.toString(), Toast.LENGTH_SHORT).show();

        })
        viewModel.isLoading?.observe(this, Observer { visibility ->
            binding.progressBar.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
        })
    }
}
