package com.masterdetailcodingchallenge.feature_itunes_search.presentation.result_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import com.google.android.material.snackbar.Snackbar
import com.masterdetailcodingchallenge.common.base.BaseFragment
import com.masterdetailcodingchallenge.common.util.hide
import com.masterdetailcodingchallenge.common.util.show
import com.masterdetailcodingchallenge.common.util.showSnackBar
import com.masterdetailcodingchallenge.databinding.FragmentResultListBinding
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.presentation.ItunesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultListFragment : BaseFragment<FragmentResultListBinding>(),
    ResultListPagerAdapter.Interaction{

    private val viewModel: ItunesSearchViewModel by viewModels()

    private var pagingDataForSearch: PagingData<Result>? = null

    private val adapter: ResultListPagerAdapter by lazy {
        ResultListPagerAdapter(this)

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResultListBinding {
        return FragmentResultListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.textSearch.clearFocus()
            binding.textSearch.setText("")
            adapter.refresh()
        }
        adapter.addLoadStateListener { loadState ->
            binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading

            if (loadState.refresh is LoadState.Error) {
                binding.root.showSnackBar(
                    "Could not refresh results: ${
                        (loadState.refresh as LoadState.Error).error.message
                    }"
                )
            }

            if (loadState.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.recyclerView.hide()
                binding.tvEmpty.show()
            } else {
                binding.recyclerView.show()
                binding.tvEmpty.hide()
            }
        }

        binding.textSearch.doOnTextChanged { text, start, before, count ->
            pagingDataForSearch?.let {
                val filtered = it.filter { result ->
                    result.title.lowercase().contains(text.toString().lowercase()) ||
                            result.artistName.lowercase().contains(text.toString().lowercase()) ||
                            result.primaryGenreName.lowercase().contains(text.toString().lowercase())
                }

                adapter.submitData(viewLifecycleOwner.lifecycle, filtered)

                if (adapter.itemCount == 0) {
                    binding.recyclerView.hide()
                    binding.tvEmpty.text = "No Result Found"
                    binding.tvEmpty.show()
                } else {
                    binding.recyclerView.show()
                    binding.tvEmpty.hide()
                }
            }
        }
    }

    override fun initObservers() {
        viewModel.pagedResultLiveData.observe(viewLifecycleOwner) { pagingData ->
            pagingDataForSearch = pagingData
            if (binding.textSearch.text.isEmpty()) {
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    override fun onItemClick(position: Int, result: Result) {
        val action = ResultListFragmentDirections
            .actionResultListFragmentToResultDetailFragment(result)
        findNavController().navigate(action)
    }

    override fun updateFavoriteStatus(result: Result) {
        viewModel.updateFavoriteStatus(result)

        if (result.isFavorite) {
            binding.root.showSnackBar(
                message = "Set as favorite",
                duration = Snackbar.LENGTH_SHORT
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.textSearch.clearFocus()
        binding.textSearch.setText("")
        binding.recyclerView.scrollToPosition(0)
    }


}