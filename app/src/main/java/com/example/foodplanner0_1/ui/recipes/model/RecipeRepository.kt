package com.example.foodplanner0_1.ui.recipes.model

import android.content.Context
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import java.util.UUID


class RecipeRepository private constructor(
    context: Context, private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val database: RecipeDatabase = RecipeDatabase.get()


    //Functions that the Database can actually work with
    fun getRecipes(): List<Recipe> = database.recipeDao().getRecipes()

    suspend fun getRecipe(id: UUID): Recipe = database.recipeDao().getRecipe(id)

    suspend fun addRecipe(recipe: Recipe) {
        database.recipeDao().addRecipe(recipe)
    }

    companion object {
        private var INSTANCE: RecipeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                RecipeDatabase.initialize(context)
                INSTANCE = RecipeRepository(context)
            }
        }

        fun get(): RecipeRepository {
            return INSTANCE
                ?: throw IllegalStateException("RecipeRepository must be initialized")
        }
    }

}