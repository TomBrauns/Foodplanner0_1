package com.example.foodplanner0_1.ui.recipes.model

import android.content.Context
import androidx.room.Room
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "recipe-database"


class RecipeRepository private constructor(
    context: Context, private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val database: RecipeDatabase = Room.databaseBuilder(
        context.applicationContext, RecipeDatabase::class.java, DATABASE_NAME
    ).build()

    //Functions that the Database can actually work with
    fun getRecipes(): Flow<List<Recipe>> = database.recipeDao().getRecipes()

    suspend fun getRecipe(id: UUID): Recipe = database.recipeDao().getRecipe(id)

    suspend fun addRecipe(recipe: Recipe) {
        database.recipeDao().addRecipe(recipe)
    }

    companion object {
        private var INSTANCE: RecipeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RecipeRepository(context)
            }
        }

        fun get(): RecipeRepository {
            return INSTANCE
                ?: throw IllegalStateException("RecipeRepository must be initialized")
        }
    }

}