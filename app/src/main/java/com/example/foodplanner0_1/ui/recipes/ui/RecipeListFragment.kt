package com.example.foodplanner0_1.ui.recipes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.recipes.ui.addrecipe.AddRecipe
import com.example.foodplanner0_1.ui.recipes.ui.recipedetail.RecipeDetail
import com.example.foodplanner0_1.ui.recipes.viewmodel.RecipeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


private const val TAG = "RecipeListFragment"


class RecipeListFragment : Fragment(), OnRecipeSelected {
    //private var _binding: FragmentRecipesBinding? = null

    private lateinit var addRecipeButton : FloatingActionButton
    private lateinit var recipesRecylerView : RecyclerView
    private lateinit var adapter : RecipeListAdapter
    //val recipeDao = RecipeDatabase.get()

//    private val binding
//        get() = checkNotNull(_binding){
//            "Cannot access Binding (because it is null. Is the view visible?"
//        }

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        addRecipeButton = view.findViewById(R.id.add_recipe_button)
        addRecipeButton.bringToFront()

        recipesRecylerView = view.findViewById(R.id.recipes_list_recycler_view)
        //_binding = FragmentRecipesBinding.inflate(inflater, container, false)

        //binding.recipesListRecyclerView.layoutManager = LinearLayoutManager(context)

        Log.d("ADD.RECIPE", addRecipeButton.toString())
        addRecipeButton.setOnClickListener(){
            val recipeFragment = AddRecipe.newInstance()

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
                .addToBackStack(null)
                .commit()
        }

        val room = RecipeDatabase.get()

        lifecycleScope.launch{
            var recipes = room.recipeDao().getRecipes()
            showRecipes(recipes)
        }

        return view
    }

    private fun showRecipes(recipes : List<Recipe>){
        val recipesList = ArrayList<Recipe>(recipes)

        //Toast.makeText(context, "" + recipes.size + " --- " + recipesList.size, Toast.LENGTH_SHORT).show()

        val adapter = RecipeListAdapter(requireContext()!!, recipesList, this)
        recipesRecylerView.adapter = adapter
        adapter.notifyItemRangeInserted(0, recipesList.size)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recipesRecylerView = view.findViewById(R.id.recipes_list_recycler_view)
        recipesRecylerView.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    override fun onItemClick(item: Recipe) {
        val recipeFragment = RecipeDetail.newInstance(item.id.toString(), "RECIPES")

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
            .addToBackStack(null)
            .commit()
    }

}