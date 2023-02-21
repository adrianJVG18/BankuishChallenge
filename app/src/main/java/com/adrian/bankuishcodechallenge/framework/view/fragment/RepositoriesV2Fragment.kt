package com.adrian.bankuishcodechallenge.framework.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
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
import com.adrian.bankuishcodechallenge.framework.utils.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RepositoriesV2Fragment : Fragment(R.layout.fragment_repositories_v2) {

    private val viewmodel: RepositoriesViewmodel by activityViewModels()
    private val binding: FragmentRepositoriesV2Binding by viewBinding(FragmentRepositoriesV2Binding::bind)
    private lateinit var navController: NavController
    private val adapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter(mutableListOf(), itemClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setUpViews()
        observeData()
    }

    private fun setUpViews() {
        binding.repositoriesRecyclerView.adapter = adapter
        binding.repositoriesRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun observeData() {
        viewmodel.repositories.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Output.Success -> {
                    handleState(true)
                    toast("Obtained ${result.data.size} items")
                    // TODO should be submit (uses DiffUtils), not update (uses notifyDataSetChanged()) but somehow submit isn't working
                    // adapter.submitList(result.data)
                    adapter.updateList(result.data)
                }
                is Output.Failure -> {
                    toast("Failed to fetch Repositories: ${result.errorMessage}")
                    handleState(false)
                }
                else -> {}
            }
        }
    }

    private fun handleState(isSuccess: Boolean) {
        binding.repositoriesRecyclerView.visibility = if (isSuccess) View.VISIBLE else View.GONE
        binding.errorStateImageview.visibility = if (isSuccess) View.GONE else View.VISIBLE
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private val itemClickListener = object : RepositoriesAdapter.OnItemClickListener {
        override fun onItemClick(item: RepositoryUi) {
            navController.navigate(
                R.id.action_repositoriesV2Fragment_to_repositoryDetailFragment,
                item.toBundle()
            )
        }
    }
}