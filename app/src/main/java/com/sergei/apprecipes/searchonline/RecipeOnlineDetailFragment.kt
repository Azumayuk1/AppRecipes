package com.sergei.apprecipes.searchonline

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sergei.apprecipes.R
import com.sergei.apprecipes.databinding.FragmentRecipeOnlineDetailBinding

class RecipeOnlineDetailFragment : Fragment() {
    private val TAG = "RecipeOnlineDetailFragment"

    private val viewModel: SearchOnlineViewModel by activityViewModels()
    private val navigationArgs: RecipeOnlineDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentRecipeOnlineDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeOnlineDetailBinding.inflate(inflater)

        binding.recipeId = navigationArgs.recipeId
        viewModel.retrieveRecipeById(binding.recipeId)

        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_recipe_online_detail)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
            setSubtitle(R.string.recipe)
        }

        viewModel.currentRecipe.observe(this.viewLifecycleOwner) { selectedItem ->
            Log.d(TAG, "Recipe retrieved, name:${selectedItem?.title ?: "Null"}")
            binding.recipe = selectedItem
        }

        Log.d(TAG, "Recipe binded, title: ${binding.recipe?.title}")
        //binding.executePendingBindings()

        return binding.root
    }


}