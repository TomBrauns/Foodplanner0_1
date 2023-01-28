package com.example.foodplanner0_1.ui.recipes.data

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.util.*


class RecipePreset (private val context: Context) : RoomDatabase.Callback(){
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch { initRecipes(context) }

    }

    private fun loadJSONArray(context: Context) : JSONArray?{
        val inputStream = context.resources.openRawResource(R.raw.recipes_catalog)
        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
    private suspend fun initRecipes(context: Context){
        val dao = RecipeDatabase.get().recipeDao()
        try{
            val recipes = loadJSONArray(context)
            if (recipes != null){
                for ( i in 0 until recipes.length()){
                    val item = recipes.getJSONObject(i)
                    val id = UUID.randomUUID()
                    val title = item.getString("title")
                    val effort = item.getString("effort")
                    val description = item.getString("description")
                    val ingredients = item.getString("ingredients")
                    val imageFileURL = item.getString("image_file_url")

                    val recipe = Recipe(
                        id = id,
                        title = title,
                        effort = effort,
                        description = description,
                        ingredients = ingredients,
                        //imageFileURL = imageFileURL
                        )
                    dao?.addRecipe(recipe)

                }
            }
        }
        catch (e:JSONException){
            Log.e("JSONException",e.toString())
        }
    }

}