package com.example.foodplanner0_1.ui.recipes.model


import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.RecipesListItem


class RecipesNavigationRepository private constructor(){

    lateinit private var fileManager: FileManager

    var recipesCatalog = mutableListOf<RecipesListItem>()

    companion object {
        private var INSTANCE: RecipesNavigationRepository? = null

        fun initialize(fileManager: FileManager) {
            Log.d("XXX", "YYY1")
            if (INSTANCE == null) {
                INSTANCE = RecipesNavigationRepository()
            }
            INSTANCE?.fileManager = fileManager
            INSTANCE?.readRecipesCatalog()
            Log.d("XXX", "YYY2")
        }

        fun get(): RecipesNavigationRepository {
            return INSTANCE
                ?: throw IllegalStateException("LogovidNavigationRepository not initialized.")
        }
    }

    private fun processRecipesJsonFile(jo: JsonObject) {
        val recipesJsonArray: JsonArray<JsonObject>? = jo.array("recipes")

        if (recipesJsonArray != null) {
            for (recipesJsonObject in recipesJsonArray) {
                val c = RecipesListItem(
                    productId = recipesJsonObject.string("product_id") ?: "",
                    title = recipesJsonObject.string("title") ?: "",
                    description = recipesJsonObject.string("description") ?: "",
                    ingredients = recipesJsonObject.string("ingredients") ?: "",
                    imageFileURL = recipesJsonObject.string("image_file_url") ?: ""
                )
                recipesCatalog.add(c)
            }
        }
    }

    private fun readRecipesCatalog() {
        val resId = R.raw.recipes_catalog
        val inputStream = fileManager.readFileFromAppRessources(resId)

        val parser = Parser.default()
        val jo = parser.parse(inputStream) as JsonObject

        processRecipesJsonFile(jo)
    }

}