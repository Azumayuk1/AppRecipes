package com.sergei.apprecipes.searchlocal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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

    // URI for the picture that user chooses from their device
    private var chosenPictureUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNewRecipeBinding.inflate(inflater)

        binding.toolbar.inflateMenu(R.menu.toolbar_add_new_recipe)

        // Setting up toolbar
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setSubtitle(R.string.add_new_recipe)

        return binding.root
    }

    // Selecting image from gallery
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            if (uri != null) {
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            chosenPictureUri = uri

            Snackbar
                .make(
                    binding.root,
                    getString(R.string.picture_added_success),
                    Snackbar.LENGTH_SHORT
                )
                .show()

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddPicture.setOnClickListener {
            selectImageFromGalleryResult.launch(arrayOf("image/*"))
        }

        // Add new recipe
        binding.addNewRecipeButton.setOnClickListener {
            if (viewModel.isNewRecipeCorrect
                    (
                    binding.inputRecipeName.editText?.text.toString(),
                    binding.inputRecipeText.editText?.text.toString()
                )
            ) {
                viewModel.addNewRecipe(
                    chosenPictureUri.toString(),
                    binding.inputRecipeName.editText?.text.toString(),
                    binding.inputFilter.editText?.text.toString(),
                    binding.inputIngredients.editText?.text.toString(),
                    binding.inputRecipeText.editText?.text.toString()
                )
                Log.d(TAG, "New recipe added")

                Snackbar
                    .make(
                        binding.root,
                        getString(R.string.new_recipe_added_success),
                        Snackbar.LENGTH_SHORT
                    )
                    .show()

                findNavController().navigateUp()
            } else {
                Snackbar
                    .make(
                        binding.root,
                        getString(R.string.error_add_recipe),
                        Snackbar.LENGTH_SHORT
                    )
                    .show()
            }

        }

    }

}