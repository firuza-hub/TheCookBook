package com.example.thecookbook.recipeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentRecipeDetailsBinding

class RecipeDetailsFragment : Fragment() {


    private lateinit var binding: FragmentRecipeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val args = RecipeDetailsFragmentArgs.fromBundle(requireArguments())
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipe_details, container, false
        )
        val imgUrl = args.recipe.imageUrl

        val imgView = binding.ivRecipeImage
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(requireActivity())
                .load(imgUri)
                .centerCrop()
                .into(imgView)

        }

        binding.recipe = args.recipe
        binding.lifecycleOwner = this

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(binding.root).popBackStack()
        }
        return binding.root
    }


}