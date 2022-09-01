package com.example.thecookbook.ui.recipeImages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thecookbook.R
import com.example.thecookbook.data.access.remote.models.UserMealImage
import com.example.thecookbook.databinding.FragmentRecipeImagesItemBinding

class RecipeImagesItemAdaptor: RecyclerView.Adapter<RecipeImagesItemAdaptor.RecipeImagesViewHolder>() {

    private var data = mutableListOf<UserMealImage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeImagesViewHolder {
        val binder = DataBindingUtil.inflate<FragmentRecipeImagesItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.fragment_recipe_images_item,
            parent,
            false
        )
        return RecipeImagesViewHolder(binder)
    }

    override fun onBindViewHolder(holder: RecipeImagesViewHolder, position: Int) {
        val current = data[position]
       holder.binding.image = current
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setNewData(newData: List<UserMealImage>){
        data = newData as MutableList<UserMealImage>
        notifyDataSetChanged()
    }
    class RecipeImagesViewHolder(val binding: FragmentRecipeImagesItemBinding):RecyclerView.ViewHolder(binding.root) { }
}