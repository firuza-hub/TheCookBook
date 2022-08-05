package com.example.thecookbook.recipeDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentRecipeDetailsBinding

class RecipeDetailsFragment : Fragment() {


    private lateinit var  binding: FragmentRecipeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)
    }


}