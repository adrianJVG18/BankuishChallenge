package com.adrian.bankuishcodechallenge.framework.view.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.bankuishcodechallenge.R
import com.adrian.bankuishcodechallenge.adapter.Output
import com.adrian.bankuishcodechallenge.adapter.model.RepositoryUi
import com.adrian.bankuishcodechallenge.adapter.viewmodels.RepositoriesViewmodel
import com.adrian.bankuishcodechallenge.databinding.FragmentRepositoriesV2Binding
import com.adrian.bankuishcodechallenge.framework.adapters.RepositoriesAdapter
import com.adrian.bankuishcodechallenge.framework.utils.asInt
import com.adrian.bankuishcodechallenge.framework.utils.hideKeyboard
import com.adrian.bankuishcodechallenge.framework.utils.toDp
import com.adrian.bankuishcodechallenge.framework.utils.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RepositoriesV2Fragment : Fragment(R.layout.fragment_repositories_v2) {

    private val viewmodel: RepositoriesViewmodel by activityViewModels()
    private val binding: FragmentRepositoriesV2Binding by viewBinding(FragmentRepositoriesV2Binding::bind)
    private lateinit var navController: NavController
    private val adapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter(mutableListOf(), itemClickListener, onBottomReached)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setUpViews()
        observeData()
    }

    private fun setUpViews() {
        binding.searchToolbarHost.viewmodel = this.viewmodel
        binding.searchToolbarHost.lifecycleOwner = this
        binding.repositoriesRecyclerView.adapter = adapter
        binding.repositoriesRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        with(binding.currentPageText) {
            text = (1).toString()
            binding.errorStateImageview.setOnClickListener {
                refreshRepositories(text.asInt)
            }
            binding.errorStateText.setOnClickListener {
                refreshRepositories(text.asInt)
            }
            binding.swipeRefresh.setOnRefreshListener {
                refreshRepositories(text.asInt)
            }
            binding.previousPageImageButton.setOnClickListener {
                if (text.asInt > 1) {
                    val decreasedPage = text.asInt - 1
                    text = decreasedPage.toString()
                    viewmodel.fetchRepositories(decreasedPage)
                }
            }
            binding.followingPageImageView.setOnClickListener {
                val increasedPage: Int = text.asInt + 1
                text = increasedPage.toString()
                viewmodel.fetchRepositories(increasedPage)
            }
        }
        binding.searchToolbarHost.searchEditText.setOnEditorActionListener { _, keyCode, event ->
            if (((event?.action ?: -1) == KeyEvent.ACTION_DOWN) || keyCode == EditorInfo.IME_ACTION_SEARCH
            ) {
                binding.searchToolbarHost.searchEditText.hideKeyboard()
                viewmodel.fetchRepositories()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    private fun observeData() {
        viewmodel.repositories.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Output.Success -> {
                    handleState(true)
                    binding.swipeRefresh.isRefreshing = false
                    adapter.updateList(result.data)
                    binding.repositoriesRecyclerView.scrollToPosition(0)
                }
                is Output.Failure -> {
                    binding.swipeRefresh.isRefreshing = false
                    handleState(false)
                }
                else -> {
                    if (result is Output.Loading && result.isLoading) {
                        binding.repositoriesRecyclerView.visibility = View.GONE
                        binding.errorStateViewGroup.visibility = View.GONE
                    }
                }
            }
        }
        viewmodel.totalRepositories.observe(viewLifecycleOwner) {
            viewmodel.totalRepositories.value?.let {
                val text = " / ${it.div(20)}"
                binding.maxPageTextView.text = text
            }
        }
    }

    private fun handleState(isSuccess: Boolean) {
        binding.repositoriesRecyclerView.visibility = if (isSuccess) View.VISIBLE else View.GONE
        binding.errorStateViewGroup.visibility = if (isSuccess) View.GONE else View.VISIBLE
    }

    private fun refreshRepositories(currentPage: Int? = 1) {
        viewmodel.fetchRepositories(currentPage)
    }

    private val itemClickListener = object : RepositoriesAdapter.OnItemClickListener {
        override fun onItemClick(item: RepositoryUi) {
            navController.navigate(
                R.id.action_repositoriesV2Fragment_to_repositoryDetailFragment,
                item.toBundle()
            )
        }
    }

    private val onBottomReached = object : RepositoriesAdapter.OnBottomReachedListener {
        override fun onBottomReached(isAtBottom: Boolean) {
            with(binding.pagingControlsLinearLayout) {
                if (isAtBottom) {
                    visibility = View.VISIBLE
                    animate()
                        .setDuration(500)
                        .translationY(0f)
                } else {
                    animate()
                        .setDuration(500)
                        .translationY(this.height.toDp().toFloat())
                        .withEndAction {
                            visibility = View.GONE
                        }

                }
            }
        }
    }
}