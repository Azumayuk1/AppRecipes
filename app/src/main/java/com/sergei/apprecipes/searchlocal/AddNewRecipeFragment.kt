package com.sergei.apprecipes.searchlocal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.databinding.FragmentAddNewRecipeBinding

class AddNewRecipeFragment : Fragment() {
    val TAG = "Adding new recipe fragment"

    private val viewModel: SearchLocalViewModel by activityViewModels {
        SearchLocalViewModelFactory(
            (activity?.application as RecipesApplication).database.recipeLocalDao()
        )
    }

    private lateinit var binding: FragmentAddNewRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNewRecipeBinding.inflate(inflater)

        binding.toolbar.inflateMenu(R.menu.toolbar_add_new_recipe)

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setSubtitle(R.string.add_new_recipe)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add new recipe
        binding.addNewRecipeButton.setOnClickListener {
            if (viewModel.isNewRecipeCorrect
                    (
                    binding.inputRecipeName.editText?.text.toString(),
                    binding.inputRecipeText.editText?.text.toString()
                )
            ) {
                viewModel.addNewRecipe(
                    null, // TODO: Add recipe image
                    binding.inputRecipeName.editText?.text.toString(),
                    binding.inputFilter.editText?.text.toString(),
                    binding.inputIngredients.editText?.text.toString(),
                    binding.inputRecipeText.editText?.text.toString()
                )
                Log.d(TAG, "New recipe added")

                Snackbar
                    .make(binding.root,
                        getString(R.string.new_recipe_added_success),
                        Snackbar.LENGTH_SHORT)
                    .show()

                findNavController().navigateUp()
            } else {
                Snackbar
                    .make(binding.root,
                        getString(R.string.error_add_recipe),
                        Snackbar.LENGTH_SHORT)
                    .show()
            }

        }

    }

}