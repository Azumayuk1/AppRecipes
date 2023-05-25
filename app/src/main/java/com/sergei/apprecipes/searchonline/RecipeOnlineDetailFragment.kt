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
import com.google.android.material.snackbar.Snackbar
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.databinding.FragmentRecipeOnlineDetailBinding

class RecipeOnlineDetailFragment : Fragment() {
    private val TAG = "RecipeOnlineDetailFragment"

    private val viewModel: SearchOnlineViewModel by activityViewModels {
        SearchOnlineViewModelFactory(
            (activity?.application as RecipesApplication).database.recipeLocalDao()
        )
    }

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

        // Toolbar
        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_recipe_online_detail)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
            setSubtitle(R.string.recipe)
        }

        // Observing the recipe
        viewModel.currentRecipe.observe(this.viewLifecycleOwner) { selectedItem ->
            Log.d(TAG, "Recipe retrieved, name:${selectedItem?.title ?: "Null"}")
            binding.recipe = selectedItem
        }

        Log.d(TAG, "Recipe binded, title: ${binding.recipe?.title}")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Downloading recipe
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save_recipe_to_device -> {
                    viewModel.downloadRecipe()
                    Log.d(TAG, "Called recipe saving to device")

                    Snackbar
                        .make(
                            binding.root,
                            getString(R.string.recipe_download_success),
                            Snackbar.LENGTH_SHORT
                        )
                        .show()

                    findNavController().navigateUp()
                }
            }
            true
        }
    }


}