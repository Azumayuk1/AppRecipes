package com.sergei.apprecipes.searchlocal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication

class RecipeDetailFragment : Fragment() {
    private val viewModel: SearchLocalViewModel by activityViewModels {
        SearchLocalViewModelFactory(
            (activity?.application as RecipesApplication).database.recipeLocalDao()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

}