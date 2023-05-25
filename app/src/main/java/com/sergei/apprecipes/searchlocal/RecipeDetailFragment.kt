package com.sergei.apprecipes.searchlocal

import android.content.Intent
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val id = navigationArgs.recipeId
        Log.d(TAG, "Recipe id: ${id}")

        // Observing recipe
        viewModel.retrieveRecipeById(id).observe(this.viewLifecycleOwner) { selectedItem ->
            Log.d(TAG, "Recipe retrieved, name:${selectedItem?.name ?: "Null"}")
            binding.recipe = selectedItem
        }

        // Toolbar
        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_recipe_detail)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
            setSubtitle(R.string.recipe)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up Toolbar options
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_recipe -> {
                    viewModel.deleteRecipe(binding.recipe)
                    Log.d(TAG, "Called recipe deletion")

                    Snackbar
                        .make(
                            binding.root,
                            getString(R.string.recipe_deleted_success),
                            Snackbar.LENGTH_SHORT
                        )
                        .show()

                    findNavController().navigateUp()
                }
                R.id.edit_recipe -> {
                    findNavController().navigate(
                        RecipeDetailFragmentDirections
                            .actionRecipeDetailFragmentToEditRecipeFragment(
                                navigationArgs.recipeId
                            )
                    )
                }
                R.id.share_recipe -> {
                    val intent = Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sharing_my_recipe))
                        .putExtra(
                            Intent.EXTRA_TEXT,
                            "${binding.recipe?.name}:\n" +
                                    "You will need:\n${binding.recipe?.ingredients}\n" +
                                    "How to cook it:\n${binding.recipe?.instructions}")
                    startActivity(intent)
                }
            }
            true
        }

    }

}