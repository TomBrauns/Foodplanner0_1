package com.example.foodplanner0_1.ui.recipes.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.RecipesListItemBinding
import com.example.foodplanner0_1.ui.recipes.data.Recipe


class RecipeHolder(
    private val binding: RecipesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recipe: Recipe, listener : OnRecipeSelected) {

        binding.recipesLabel.text = recipe.title
        binding.recipeEffort.text = recipe.effort // This is the new element
        //binding.recipesDescription.text = recipe.description (This does not exist in this Fragment ( or shouldnt, hard to test without a working project ... :D )
        binding.recipesIngredients.text = recipe.description

        //itemView.background = ColorDrawable(Color.GRAY)

        binding.recipesButton.setOnClickListener {
            listener.onItemClick(recipe)
        }
    }
}

interface OnRecipeSelected{
    fun onItemClick(item : Recipe)
}

class RecipeListAdapter(
    context : Context,
    private val recipes: List<Recipe>,
    var listener: OnRecipeSelected
) : RecyclerView.Adapter<RecipeHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipesListItemBinding.inflate(inflater, parent, false)
        return RecipeHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, listener)
    }

    override fun getItemCount() = recipes.size
}