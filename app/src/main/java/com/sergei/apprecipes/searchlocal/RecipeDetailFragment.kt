package com.sergei.apprecipes.searchlocal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {
    private val TAG = "RecipeDetail"

    private val navigationArgs: RecipeDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRecipeDetailBinding

    private val viewModel: SearchLocalViewModel by activityViewModels {
        SearchLocalViewModelFactory(
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
        // Inflate the layout for this fragment
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val id = navigationArgs.recipeId
        Log.d(TAG, "Recipe id: ${id}")

        viewModel.retrieveRecipeById(id).observe(this.viewLifecycleOwner) { selectedItem ->
            Log.d(TAG, "Recipe retrieved, name:${selectedItem.name}")
            binding.recipe = selectedItem
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}