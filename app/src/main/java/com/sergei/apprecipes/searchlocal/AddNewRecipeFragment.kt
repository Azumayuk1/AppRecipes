package com.sergei.apprecipes.searchlocal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sergei.apprecipes.R
import com.sergei.apprecipes.databinding.FragmentAddNewRecipeBinding

class AddNewRecipeFragment : Fragment() {
    private lateinit var binding: FragmentAddNewRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewRecipeBinding.inflate(inflater)

        binding.toolbar.inflateMenu(R.menu.toolbar_add_new_recipe)

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setSubtitle(R.string.add_new_recipe)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}