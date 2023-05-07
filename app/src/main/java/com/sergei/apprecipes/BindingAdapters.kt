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
import com.sergei.apprecipes.network.SpoonacularRecipeResponse


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
    // TODO: Add error placeholder
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
        if (recipe.dishCategories[0].isNotBlank()) {
            chip.text = recipe.dishCategories[0]
        }
    }
}

@BindingAdapter("recipeOnlineIngredients")
fun bindRecipeOnlineIngredients(textView: TextView, recipe: SpoonacularRecipeResponse?) {
    val ingredientsText = java.lang.StringBuilder("")
    if (recipe != null) {
        for (i in recipe.ingredients) {
            ingredientsText.append(i.info + "\n")
        }

        textView.text = ingredientsText.toString()
    }
}

@BindingAdapter("recipeOnlineInstructions")
fun bindRecipeOnlineInstructions(textView: TextView, recipe: SpoonacularRecipeResponse?) {
    if(!recipe?.summary.isNullOrBlank()) {
        textView.text = recipe?.summary
    }

}




