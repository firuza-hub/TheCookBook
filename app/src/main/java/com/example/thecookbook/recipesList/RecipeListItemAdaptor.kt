package com.example.thecookbook.recipesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thecookbook.R
import com.example.thecookbook.data.models.RecipeDataItem
import com.example.thecookbook.databinding.FragmentRecipeDetailsBinding
import kotlinx.coroutines.flow.callbackFlow

class RecipeListItemAdaptor (private val callback: ((item: RecipeDataItem) -> Unit)? = null) :
RecyclerView.Adapter<RecipeListItemViewHolder>() {

    var data = mutableListOf<RecipeDataItem>(RecipeDataItem(123, "test recipe"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListItemViewHolder {
        val binder = DataBindingUtil.inflate<FragmentRecipeDetailsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.fragment_recipes_item,
            parent,
            false
        )

        return RecipeListItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: RecipeListItemViewHolder, position: Int) {
        val currentItem = data[position]
        holder.binding.apply { item = currentItem}

        holder.itemView.setOnClickListener{callback?.invoke(currentItem)}
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class RecipeListItemViewHolder(val binding: FragmentRecipeDetailsBinding): RecyclerView.ViewHolder(binding.root) {

}
