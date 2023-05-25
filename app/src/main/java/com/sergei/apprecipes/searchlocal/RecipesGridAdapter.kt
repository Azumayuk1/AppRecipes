package com.sergei.apprecipes.searchlocal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergei.apprecipes.database.RecipeLocal
import com.sergei.apprecipes.databinding.RecipeGridItemBinding

class RecipesGridAdapter(private val onItemClicked: (RecipeLocal) -> Unit) :
    ListAdapter<RecipeLocal, RecipesGridAdapter.RecipeViewHolder>(DiffCallback) {

        class RecipeViewHolder(private var binding: RecipeGridItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(recipe: RecipeLocal) {
                binding.recipe = recipe
                binding.executePendingBindings()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecipeGridItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)

        // Navigating to recipe details
        holder.itemView.setOnClickListener {
            val action =
                SearchLocalFragmentDirections
                    .actionSearchLocalFragmentToRecipeDetailFragment(recipe.id)
            holder.itemView.findNavController().navigate(action)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RecipeLocal>() {
        override fun areItemsTheSame(oldItem: RecipeLocal, newItem: RecipeLocal): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: RecipeLocal, newItem: RecipeLocal): Boolean {
            return (oldItem.name == newItem.name) && (oldItem.imagePath == newItem.imagePath)
        }

    }
}