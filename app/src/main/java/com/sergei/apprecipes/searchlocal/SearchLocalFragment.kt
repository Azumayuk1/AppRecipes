package com.sergei.apprecipes.searchlocal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sergei.apprecipes.LoginActivity
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.databinding.FragmentSearchLocalBinding

class SearchLocalFragment : Fragment() {

    private val TAG = "SearchLocalFragment"

    private val viewModel: SearchLocalViewModel by activityViewModels {
        SearchLocalViewModelFactory(
            (activity?.application as RecipesApplication).database.recipeLocalDao()
        )
    }

    private lateinit var binding: FragmentSearchLocalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchLocalBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        // Setting recipe adapter
        val adapter = RecipesGridAdapter {
            val action =
                SearchLocalFragmentDirections.actionSearchLocalFragmentToRecipeDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recipesView.adapter = adapter
        Log.d(TAG, "Adapter for Recycler set")

        // Submitting list for RecyclerView
        viewModel.recipes.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
            Log.d(SearchLocalFragment::class.simpleName, "Local recipes: " + items?.size)
        }

        binding.addNewRecipeButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchLocalFragment_to_addNewRecipeFragment)
        }

        // Setting up the search bar
        binding.searchBar.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                val adapter = binding.recipesView.adapter
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchBar.clearFocus()

                    if (!query.isNullOrBlank()) {
                        viewModel.loadSearchedRecipes(query)
                        binding.recipesView.requestFocus()
                        return false
                    } else {
                        viewModel.loadAllRecipes()
                        binding.recipesView.requestFocus()
                        return false
                    }
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) {
                        viewModel.loadAllRecipes()
                        return false
                    } else {
                        viewModel.loadSearchedRecipes(newText)
                    }
                    return false
                }
            }
        )
    }

}