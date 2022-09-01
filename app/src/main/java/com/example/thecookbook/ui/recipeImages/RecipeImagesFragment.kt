package com.example.thecookbook.ui.recipeImages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecookbook.R
import com.example.thecookbook.databinding.FragmentRecipeImagesBinding
import com.example.thecookbook.ui.camera.CameraFragmentArgs
import com.example.thecookbook.ui.camera.CameraViewModel


class RecipeImagesFragment : Fragment() {

    private lateinit var recipeId: String
    private lateinit var binding: FragmentRecipeImagesBinding
    private val _viewModel: CameraViewModel by viewModels {
        CameraViewModel.Factory(
            requireActivity().application,
            recipeId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        recipeId = CameraFragmentArgs.fromBundle(requireArguments()).recipeId

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_images, container, false)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvMealImages.adapter = RecipeImagesItemAdaptor()
        binding.rvMealImages.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        }
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel.pics.observe(viewLifecycleOwner) {
            (binding.rvMealImages.adapter as RecipeImagesItemAdaptor).setNewData(it)
        }
    }
}