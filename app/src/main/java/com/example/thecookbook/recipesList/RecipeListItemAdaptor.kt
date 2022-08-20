package com.example.thecookbook.recipesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thecookbook.R
import com.example.thecookbook.data.models.RecipeDataItem
import com.example.thecookbook.databinding.FragmentRecipesItemBinding

class RecipeListItemAdaptor (private val callback: ((item: RecipeDataItem) -> Unit)? = null, val context: Context) :
RecyclerView.Adapter<RecipeListItemViewHolder>() {

    private var data = mutableListOf<RecipeDataItem>()

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
        holder.binding.apply { item = currentItem}

//        currentItem.imageUrl?.let {
//            val imgUri = currentItem.imageUrl.toUri().buildUpon().scheme("https").build()
//            Glide.with(context)
//                .load(imgUri)
//                .into(holder.binding.imageView)
//        }
        holder.itemView.setOnClickListener{callback?.invoke(currentItem)}
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setNewData(newData: List<RecipeDataItem>){
        data = newData as MutableList<RecipeDataItem>
        notifyDataSetChanged()
    }
}

class RecipeListItemViewHolder(val binding: FragmentRecipesItemBinding): RecyclerView.ViewHolder(binding.root) {

}
