package com.sergei.apprecipes.searchonline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.databinding.FragmentSearchOnlineBinding

class SearchOnlineFragment : Fragment() {
    private lateinit var binding: FragmentSearchOnlineBinding

    private val viewModel: SearchOnlineViewModel by activityViewModels {
        SearchOnlineViewModelFactory(
            (activity?.application as RecipesApplication).database.recipeLocalDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchOnlineBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val adapter = RecipesOnlineGridAdapter()
        binding.recipesRecyclerView.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        with(binding.searchBar) {
            isIconified = false
        }

        binding.searchBar.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchBar.clearFocus()
                    if (!query.isNullOrBlank()) {
                        viewModel.searchRecipes(query)
                        return false
                    } else {
                        Snackbar
                            .make(binding.root,
                                getString(R.string.search_is_empty),
                                Snackbar.LENGTH_SHORT)
                            .show()
                        return false
                    }
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
    }
}