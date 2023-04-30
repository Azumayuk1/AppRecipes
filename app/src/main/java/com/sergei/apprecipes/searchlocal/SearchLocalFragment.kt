package com.sergei.apprecipes.searchlocal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        binding.recipesView.adapter =  adapter
        Log.d(TAG, "Adapter for Recycler set")

        // Submitting list for RecyclerView
        viewModel.recipes.observe(this.viewLifecycleOwner) {
            items -> items.let { adapter.submitList(it) }
        }

        binding.addNewRecipeButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchLocalFragment_to_addNewRecipeFragment)
        }
    }

}