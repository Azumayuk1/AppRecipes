package com.sergei.apprecipes.searchonline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sergei.apprecipes.R
import com.sergei.apprecipes.databinding.FragmentRecipeOnlineDetailBinding

class RecipeOnlineDetailFragment : Fragment() {
    private val viewModel: SearchOnlineViewModel by activityViewModels()
    private lateinit var binding: FragmentRecipeOnlineDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeOnlineDetailBinding.inflate(inflater)
        return binding.root
    }


}