package com.example.thecookbook.ui.recipesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thecookbook.R
import com.example.thecookbook.data.access.remote.models.Recipe
import com.example.thecookbook.databinding.FragmentRecipesItemBinding

class RecipeListItemAdaptor (private val callback: ((item: Recipe) -> Unit)? = null, val context: Context) :
RecyclerView.Adapter<RecipeListItemViewHolder>() {

    private var data = mutableListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListItemViewHolder {
        val binder = DataBindingUtil.inflate<FragmentRecipesItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.fragment_recipes_item,
            parent,
            false
        )

        return RecipeListItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: RecipeListItemViewHolder, position: Int) {
        val currentItem = data[position]
        holder.binding.apply { recipe = currentItem}
        holder.itemView.setOnClickListener{callback?.invoke(currentItem)}
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setNewData(newData: List<Recipe>){
        data = if(newData.isEmpty()) mutableListOf<Recipe>() else newData as MutableList<Recipe>
        notifyDataSetChanged()
    }
}

class RecipeListItemViewHolder(val binding: FragmentRecipesItemBinding): RecyclerView.ViewHolder(binding.root) {

}
