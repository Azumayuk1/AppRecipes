package com.sergei.apprecipes

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.sergei.apprecipes.database.RecipeLocal
import com.sergei.apprecipes.network.OnlineRecipeBasic
import com.sergei.apprecipes.searchonline.RecipesOnlineGridAdapter

@BindingAdapter("recipeImage")
fun bindRecipeImage(imgView: ImageView, recipeLocal: RecipeLocal?) {
    if (recipeLocal?.imagePath.isNullOrBlank()) {
        imgView.setImageResource(R.drawable.placeholder_food)
    } else {
        // TODO: Logic for loading image from database
    }
}

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

@BindingAdapter("recipeName")
fun bindRecipeName(textView: TextView, recipeLocal: RecipeLocal?) {
    textView.text = recipeLocal?.name
}

@BindingAdapter("recipeTitle")
fun bindRecipeTitle(textView: TextView, title: String?) {
    textView.text = title ?: "No title"
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

@BindingAdapter("recipesList")
fun bindRecyclerViewList(recyclerView: RecyclerView, data: List<OnlineRecipeBasic>?) {
    /*val adapter = recyclerView.adapter as RecipesOnlineGridAdapter
    if (data != null) {
        adapter.submitList(data)
    }*/
}
