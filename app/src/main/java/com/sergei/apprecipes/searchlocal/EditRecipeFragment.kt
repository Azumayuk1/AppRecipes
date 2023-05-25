package com.sergei.apprecipes.searchlocal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.databinding.FragmentEditRecipeBinding

class EditRecipeFragment : Fragment() {

    private val TAG = "RecipeEdit"

    private val navigationArgs: EditRecipeFragmentArgs by navArgs()
    private lateinit var binding: FragmentEditRecipeBinding

    private val viewModel: SearchLocalViewModel by activityViewModels {
        SearchLocalViewModelFactory(
            (activity?.application as RecipesApplication).database.recipeLocalDao()
        )
    }

    // URI for the picture that user chooses from their device
    private var chosenPictureUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentEditRecipeBinding.inflate(inflater, container, false)

        val id = navigationArgs.recipeId
        Log.d(TAG, "Recipe id: ${id}")

        // Observing recipe
        viewModel.retrieveRecipeById(id).observe(this.viewLifecycleOwner) { selectedItem ->
            Log.d(TAG, "Recipe retrieved, name: ${selectedItem?.name ?: "Null"}")
            binding.recipe = selectedItem
            chosenPictureUri = selectedItem.imagePath?.toUri()
            with(binding) {
                inputRecipeName.editText?.setText(selectedItem.name)
                inputFilter.editText?.setText(selectedItem.filter)
                inputIngredients.editText?.setText(selectedItem.ingredients)
                inputRecipeText.editText?.setText(selectedItem.instructions)
            }
        }

        // Setting up Toolbar
        binding.toolbar.apply {
            inflateMenu(R.menu.toolbar_edit_recipe)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { findNavController().navigateUp() }
            setSubtitle(R.string.edit_recipe)
        }

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

        // Edit recipe
        binding.saveEditedRecipe.setOnClickListener {
            if (viewModel.isNewRecipeCorrect
                    (
                    binding.inputRecipeName.editText?.text.toString(),
                    binding.inputRecipeText.editText?.text.toString()
                )
            ) {
                viewModel.editRecipe(
                    navigationArgs.recipeId,
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
                        getString(R.string.recipe_edit_success),
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