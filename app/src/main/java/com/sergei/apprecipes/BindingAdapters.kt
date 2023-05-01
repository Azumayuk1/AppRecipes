package com.sergei.apprecipes

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.sergei.apprecipes.database.RecipeLocal

@BindingAdapter("recipeImage")
fun bindRecipeImage(imgView: ImageView, recipeLocal: RecipeLocal?) {
    if (recipeLocal?.imagePath.isNullOrBlank()) {
        imgView.setImageResource(R.drawable.placeholder_food)
    } else {
        // TODO: Logic for loading image from database
    }
}

@BindingAdapter("recipeName")
fun bindRecipeName(textView: TextView, recipeLocal: RecipeLocal?) {
    textView.text = recipeLocal?.name
}

@BindingAdapter("recipeFilter")
fun bindRecipeFilter(chip: Chip, recipeLocal: RecipeLocal?) {
    chip.text = recipeLocal?.filter ?: chip.resources.getString(R.string.no_category)
}

@BindingAdapter("recipeIngredients")
fun bindRecipeIngredients(textView: TextView, recipeLocal: RecipeLocal?) {
    textView.text = recipeLocal?.ingredients
}

@BindingAdapter("recipeInstruction")
fun bindRecipeInstructions(textView: TextView, recipeLocal: RecipeLocal?) {
    textView.text = recipeLocal?.instructions
}
