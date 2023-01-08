package com.example.foodplanner0_1.ui.recipes.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.RecipesListItemBinding
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import java.util.UUID


class RecipeHolder(
    private val binding: RecipesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recipe: Recipe, onRecipeClicked: (recipeId: UUID) -> Unit) {

        binding.recipesLabel.text = recipe.title
        binding.recipeEffort.text = recipe.effort // This is the new element
        //binding.recipesDescription.text = recipe.description (This does not exist in this Fragment ( or shouldnt, hard to test without a working project ... :D )
        binding.recipesIngredients.text = recipe.ingredients

        itemView.background = ColorDrawable(Color.GRAY)

        binding.root.setOnClickListener {
            onRecipeClicked(recipe.id)
        }
    }
}

class RecipeListAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClicked: (recipeId: UUID) -> Unit
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
        holder.bind(recipe, onRecipeClicked)
    }

    override fun getItemCount() = recipes.size
}