package com.example.foodplanner0_1.ui.recipes.recipedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.FragmentRecipeDetailBinding
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import java.util.*

class RecipeDetailHolder(
    private val binding: FragmentRecipeDetailBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recipe: Recipe, onRecipeClicked: (recipeId: UUID) -> Unit) {

        binding.recipeLabel.text = recipe.title
        binding.recipeEffort.text = recipe.effort // This is the new element
        binding.recipeDescription.text = recipe.description
        binding.recipeIngredients.text = recipe.ingredients

        binding.root.setOnClickListener {
            onRecipeClicked(recipe.id)
        }
    }
}

class RecipeDetailListAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClicked: (recipeId: UUID) -> Unit
) : RecyclerView.Adapter<RecipeDetailHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeDetailHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentRecipeDetailBinding.inflate(inflater, parent, false)
        return RecipeDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeDetailHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, onRecipeClicked)
    }

    override fun getItemCount() = recipes.size
}