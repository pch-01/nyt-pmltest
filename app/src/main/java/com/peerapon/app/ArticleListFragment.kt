package com.peerapon.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.peerapon.app.adapter.ArticleListAdapter
import com.peerapon.app.extension.onQueryTextChanged
import com.peerapon.app.viewmodel.ArticleListViewModel
import com.peerapon.domain.contract.ArticleListViewState
import com.peerapon.domain.contract.Period
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListFragment : Fragment(R.layout.fragment_first),
    ArticleListAdapter.AdapterItemAction {

    @Inject
    lateinit var adapter: ArticleListAdapter

    private val viewModel: ArticleListViewModel by viewModels()

    val navController by lazy { findNavController(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        adapter.itemAction = this

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredContent.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.queryState.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel.queryState.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.one_day_period -> {
                viewModel.onPeriodUpdate(Period.ONE_DAY)
                true
            }
            R.id.thirty_days_period -> {
                viewModel.onPeriodUpdate(Period.THIRTY_DAYS)
                true
            }
            R.id.seven_days_period -> {
                viewModel.onPeriodUpdate(Period.SEVEN_DAYS)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onClick(state: ArticleListViewState) {
        goToDetailScreen(state)
    }

    private fun goToDetailScreen(article: ArticleListViewState) {
        val directions =
            ArticleListFragmentDirections.actionFirstFragmentToSecondFragment(id = article.id)
        navController.navigate(directions)
    }

}