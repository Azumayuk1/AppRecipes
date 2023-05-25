package com.sergei.apprecipes

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.sergei.apprecipes.database.RecipeLocal
import com.sergei.apprecipes.network.Ingredient
import com.sergei.apprecipes.network.SpoonacularRecipeResponse
import com.sergei.apprecipes.network.prepareSpoonacularInstructions


// Adapters for local recipes

@BindingAdapter("recipeImage")
fun bindRecipeImage(imgView: ImageView, recipeLocal: RecipeLocal?) {
    if (recipeLocal?.imagePath.isNullOrBlank()) {
        imgView.setImageResource(R.drawable.placeholder_food)
    } else {
        Glide.with(imgView)
            .load(recipeLocal?.imagePath.toString().toUri())
            .placeholder(R.drawable.placeholder_food)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(FitCenter())
            .into(imgView)
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

// Adapters for recipes loaded from Spoonacular API

@BindingAdapter("recipeImageUrl")
fun bindRecipeImageUrl(imgView: ImageView, imageUrl: String?) {
    Glide.with(imgView)
        .load(imageUrl)
        .placeholder(R.drawable.placeholder_food)
        .transition(DrawableTransitionOptions.withCrossFade())
        .transform(FitCenter())
        .into(imgView)
}

@BindingAdapter("recipeTitle")
fun bindRecipeTitle(textView: TextView, title: String?) {
    textView.text = title ?: "No title"
}

@BindingAdapter("recipeOnlineCategory")
fun bindRecipeOnlineCategory(chip: Chip, recipe: SpoonacularRecipeResponse?) {
    if (recipe != null) {
        if (!recipe.dishCategories.isEmpty()) {
            if (recipe.dishCategories[0].isNotBlank()) {
                chip.text = recipe.dishCategories[0]
            }
        }

    }
}

// Prepares ingredients to be shown as a list
fun prepareRecipeOnlineIngredients(ingredients: List<Ingredient>?): String {
    val ingredientsText = java.lang.StringBuilder("")
    if (ingredients != null) {
        for (i in ingredients) {
            ingredientsText.append(i.info + "\n")
        }
    }
    return ingredientsText.toString()
}

@BindingAdapter("recipeOnlineIngredients")
fun bindRecipeOnlineIngredients(textView: TextView, recipe: SpoonacularRecipeResponse?) {
    if (recipe != null) {
        textView.text = prepareRecipeOnlineIngredients(recipe.ingredients)
    }
}

@BindingAdapter("recipeOnlineInstructions")
fun bindRecipeOnlineInstructions(textView: TextView, recipe: SpoonacularRecipeResponse?) {
    if (!recipe?.summary.isNullOrBlank()) {
        textView.text = prepareSpoonacularInstructions(recipe?.summary)
    }

}




