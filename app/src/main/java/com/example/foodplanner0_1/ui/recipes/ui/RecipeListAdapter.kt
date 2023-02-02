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
        binding.recipeEffort.text = recipe.effort
        binding.recipesDescription.text = recipe.description

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