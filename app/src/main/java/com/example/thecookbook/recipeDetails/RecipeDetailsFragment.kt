package com.example.thecookbook.recipeDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentRecipeDetailsBinding
import com.example.thecookbook.recipesList.RecipesListViewModel

class RecipeDetailsFragment : Fragment() {


    private lateinit var  binding: FragmentRecipeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = RecipeDetailsFragmentArgs.fromBundle(requireArguments())

        binding  = DataBindingUtil.inflate(inflater,
            R.layout.fragment_recipe_details, container, false)
binding.recipe = args.recipe
         binding.lifecycleOwner = this

        return binding.root
    }


}