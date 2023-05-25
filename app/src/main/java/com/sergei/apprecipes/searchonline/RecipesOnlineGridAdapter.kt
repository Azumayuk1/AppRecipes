package com.sergei.apprecipes.searchonline

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergei.apprecipes.databinding.RecipeOnlineGridItemBinding
import com.sergei.apprecipes.network.OnlineRecipeBasic

class RecipesOnlineGridAdapter :
    ListAdapter<OnlineRecipeBasic, RecipesOnlineGridAdapter.RecipeViewHolder>(DiffCallback) {

    class RecipeViewHolder(private var binding: RecipeOnlineGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: OnlineRecipeBasic) {
            binding.recipe = recipe
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val TAG = "RecipeOnline ViewHolder"
        Log.d(TAG, "Created")
        return RecipeViewHolder(
            RecipeOnlineGridItemBinding.inflate(
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
                SearchOnlineFragmentDirections
                    .actionSearchOnlineFragmentToRecipeOnlineDetailFragment(recipe.id)
            holder.itemView.findNavController().navigate(action)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OnlineRecipeBasic>() {
        override fun areItemsTheSame(
            oldItem: OnlineRecipeBasic,
            newItem: OnlineRecipeBasic
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OnlineRecipeBasic,
            newItem: OnlineRecipeBasic
        ): Boolean {
            if (oldItem.title == newItem.title && oldItem.imageSrc == newItem.imageSrc) {
                return true
            }
            return false
        }
    }
}