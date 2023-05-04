package com.sergei.apprecipes.searchonline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sergei.apprecipes.R
import com.sergei.apprecipes.databinding.FragmentSearchOnlineBinding

class SearchOnlineFragment : Fragment() {
    private lateinit var binding: FragmentSearchOnlineBinding
    private val viewModel: SearchOnlineViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchOnlineBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = RecipesOnlineGridAdapter()
        binding.recipesRecyclerView.adapter = adapter

        viewModel.recipes.observe(this.viewLifecycleOwner) {
            items -> items.let { adapter.submitList(it) }
        }

        //binding.recipesRecyclerView.adapter?.notifyDataSetChanged()
    }
}