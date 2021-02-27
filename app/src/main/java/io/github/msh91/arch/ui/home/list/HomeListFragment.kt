package io.github.msh91.arch.ui.home.list
import androidx.lifecycle.lifecycleScope
import io.github.msh91.arch.R
import io.github.msh91.arch.databinding.FragmentHomeListBinding
import io.github.msh91.arch.ui.base.BaseFragment
import io.github.msh91.arch.ui.base.ViewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeListFragment : BaseFragment<HomeListViewModel, FragmentHomeListBinding>() {

    override val viewModel: HomeListViewModel by getLazyViewModel(ViewModelScope.ACTIVITY)
    override val layoutId: Int = R.layout.fragment_home_list

    override fun onViewInitialized(binding: FragmentHomeListBinding) {
        super.onViewInitialized(binding)
        binding.viewModel = viewModel
        binding.adapter = ServerAdapter()

        binding.swipe.isRefreshing = true

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allServersFromDb
                .collectLatest {
                    binding.swipe.isRefreshing = false
                    binding.adapter?.submitData(it)
                }
        }


        binding.swipe.setOnRefreshListener {
            binding.adapter?.refresh()
        }

    }



}
