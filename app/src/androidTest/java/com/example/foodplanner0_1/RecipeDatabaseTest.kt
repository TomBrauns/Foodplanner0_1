package com.example.foodplanner0_1

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDao
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class SimpleExampleTest {
    private lateinit var dao: RecipeDao
    private lateinit var db: RecipeDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RecipeDatabase::class.java
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
        dao = db.recipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun isEmpty() {
        assertTrue(dao.getRecipes().isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun addAndGetRecipe() = runBlocking {
        val id = UUID.randomUUID()
        val recipe = Recipe(
            id = id,
            title = "testRecipe",
            description = "Hello",
            effort = "5 minutes",
            ingredients = "banana",
            steps = "Only 1"
        )
        dao.addRecipe(recipe)
        val newRecipe = dao.getRecipe(id)
        assertEquals(recipe.id, newRecipe.id)
        assertEquals(recipe.title, newRecipe.title)
        assertEquals(recipe.description, newRecipe.description)
        assertEquals(recipe.effort, newRecipe.effort)
        assertEquals(recipe.ingredients, newRecipe.ingredients)
        assertEquals(recipe.steps, newRecipe.steps)

    }


}