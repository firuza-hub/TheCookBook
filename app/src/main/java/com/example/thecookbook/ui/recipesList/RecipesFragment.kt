package com.example.thecookbook.ui.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecookbook.R
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    private lateinit var _viewModel: RecipesListViewModel
    private lateinit var binding: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipes, container, false
        )
        _viewModel = ViewModelProvider(this)[RecipesListViewModel::class.java]
        binding.viewModel = _viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupRecyclerView()
        binding.svRecipes.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                _viewModel.getRecipesByName((newText?.lowercase() ?: ""))
                return true
            }
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.recipes.observe(viewLifecycleOwner) {
            (binding.rvRecipesList.adapter as RecipeListItemAdaptor).setNewData(it as List<Recipe>)
        }
    }

    private fun setupRecyclerView() {
        val adapter = RecipeListItemAdaptor({
            Navigation.findNavController(binding.root)
                .navigate(RecipesFragmentDirections.actionRecipesFragmentToRecipeDetailsFragment(it))
        }, requireActivity())

        binding.rvRecipesList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        }
    }


}