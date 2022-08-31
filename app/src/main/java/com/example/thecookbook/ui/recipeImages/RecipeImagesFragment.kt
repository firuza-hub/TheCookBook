package com.example.thecookbook.ui.recipeImages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentRecipeImagesBinding
import com.example.thecookbook.ui.camera.CameraFragmentArgs
import com.example.thecookbook.ui.camera.CameraViewModel


class RecipeImagesFragment : Fragment() {

    private lateinit var binding: FragmentRecipeImagesBinding
    private lateinit var _viewModel: CameraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_images, container, false)
        _viewModel = ViewModelProvider(this)[CameraViewModel::class.java]
        val args = CameraFragmentArgs.fromBundle(requireArguments())

        binding.rvMealImages.adapter = RecipeImagesItemAdaptor()
        binding.rvMealImages.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        }
        binding.btnBack.setOnClickListener{
            Navigation.findNavController(binding.root).popBackStack()
        }
        (binding.rvMealImages.adapter as RecipeImagesItemAdaptor).setNewData(_viewModel.getPics(args.recipeId))
        return binding.root
    }
}