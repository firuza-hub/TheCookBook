package com.example.thecookbook.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.setMargins
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecookbook.R
import com.example.thecookbook.data.models.RecipeDataItem
import com.example.thecookbook.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    private lateinit var _viewModel: RecipesListViewModel
    private lateinit var binding: FragmentRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = DataBindingUtil.inflate(inflater,
                 R.layout.fragment_recipes, container, false)
        _viewModel = ViewModelProvider(this)[RecipesListViewModel::class.java]
        binding.viewModel = _viewModel//use this to pass view model fields to some binding adapter that will display loader
        binding.lifecycleOwner = this

        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.recipes.observe(viewLifecycleOwner, Observer {
            (binding.rvRecipesList.adapter as RecipeListItemAdaptor).setNewData(it as List<RecipeDataItem>)
        })
    }

    private fun setupRecyclerView() {
        val adapter = RecipeListItemAdaptor {
            Navigation.findNavController(binding.root)
                .navigate(RecipesFragmentDirections.actionRecipesFragmentToRecipeDetailsFragment(it))}

        binding.rvRecipesList.apply {
            this.adapter = adapter
            layoutManager = object : GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false){
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams) : Boolean {
                    // force width of viewHolder to be a fraction of RecyclerViews
                    // this will override layout_width from xml
                    lp.width = (width - 80) / spanCount
                    lp.setMargins(20)
                    return true
                }
            }
        }
    }


}