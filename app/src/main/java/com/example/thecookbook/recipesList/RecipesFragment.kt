package com.example.thecookbook.recipesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentRecipeDetailsBinding
import com.example.thecookbook.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    val _viewModel: RecipesListViewModel by viewModels<RecipesListViewModel>()
    private lateinit var binding: FragmentRecipesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        setupRecyclerView()
        return view

    }

    private fun setupRecyclerView() {
        val adapter = RecipeListItemAdaptor {
            Toast.makeText(requireContext(), "item clicked", Toast.LENGTH_SHORT).show()        }

        binding.rvRecipesList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

}