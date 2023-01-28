package com.example.foodplanner0_1.ui.calender.dailycalender

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.recipes.ui.recipedetail.RecipeDetail
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private const val DAY_PARAM = "dayParam"
private const val MONTH_PARAM = "monthParam"
private const val YEAR_PARAM = "yearParam"

class DailyCalender : Fragment(), DailyMealAdapter.OnMealListener {
    private var day : Int? = null
    private var month : Int? = null
    private var year : Int? = null
    private lateinit var calendar : Calendar

    private lateinit var dayNumber : TextView
    private lateinit var monthYear : TextView
    private lateinit var saveMeal : Button

    private val monthYearFormatter = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val nameWeekFormatter = SimpleDateFormat("EEEE", Locale.ENGLISH)

    private lateinit var adapter : DailyMealAdapter
    private lateinit var mealRecylerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getInt(DAY_PARAM)
            month = it.getInt(MONTH_PARAM)
            year = it.getInt(YEAR_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_calender, container, false)

        dayNumber = view.findViewById(R.id.dayNumberDaily)
        monthYear = view.findViewById(R.id.monthYearDaily)
        saveMeal = view.findViewById(R.id.saveDaily)
        mealRecylerView = view.findViewById(R.id.mealsRecyclerView)

        calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day?: 1)
        calendar.set(Calendar.MONTH, month?: 1)
        calendar.set(Calendar.YEAR, year?: 2023)

        dayNumber.text = nameWeekFormatter.format(calendar.time) + " " + calendar.get(Calendar.DAY_OF_MONTH).toString()
        monthYear.text = monthYearFormatter.format(calendar.time)

        saveMeal.setOnClickListener{
            //(activity as MainActivity).clickBottomItem(0)
            Snackbar.make(view, "Meals updated (not really)", Snackbar.LENGTH_LONG).show()
            Executors.newSingleThreadScheduledExecutor().schedule({
                parentFragmentManager.popBackStack()
                }, 600, TimeUnit.MILLISECONDS)
        }

        val room = RecipeDatabase.get()

        lifecycleScope.launch{
            var recipes = room.recipeDao().getRecipes()
            showRecipes(recipes)
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun showRecipes(recipes: List<Recipe>) {
        val foods = ArrayList<String>()
        val uuids = ArrayList<UUID?>()

        for(recipe in recipes){
            foods.add(recipe.title)
            uuids.add(recipe.id)
        }
        foods.add(MealConstants.NO_SELECTION_MEAL)
        uuids.add(null)

        val icons = listOf(R.drawable.ic_breakfast_crossant, R.drawable.ic_lunch_hambur, R.drawable.ic_baseline_cookie_24)
        val names = listOf("Breakfast", "Lunch", "Dinner")
        val defaults = listOf(MealConstants.NO_SELECTION_MEAL, MealConstants.NO_SELECTION_MEAL, MealConstants.NO_SELECTION_MEAL)
        val mealItems = ArrayList<DailyMealModel>()

        icons.forEachIndexed { index, icon ->
            val item = DailyMealModel(names[index], icon, defaults[index], foods, uuids)
            mealItems.add(item)
        }

        val layoutManager = LinearLayoutManager(context)
        mealRecylerView.layoutManager = layoutManager
        adapter = DailyMealAdapter(mealItems, requireContext(), this)
        mealRecylerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance(day: Int, month: Int, year: Int) =
            DailyCalender().apply {
                arguments = Bundle().apply {
                    putInt(DAY_PARAM, day)
                    putInt(MONTH_PARAM, month)
                    putInt(YEAR_PARAM, year)
                }
            }
    }

    override fun onRecipeSelected(item: DailyMealModel, id : UUID?, controls: DailyMealAdapter.ViewHolder) {
        if(controls.mealSpinner.selectedItem.toString() == MealConstants.NO_SELECTION_MEAL){
            Snackbar.make(saveMeal, "No meal selected", Snackbar.LENGTH_LONG).show()
            return
        }
        val recipeFragment = RecipeDetail.newInstance(id.toString(), "---")

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
            .addToBackStack(null)
            .commit()
        //Toast.makeText(context, "Recipe view not implemented", Toast.LENGTH_SHORT).show()
    }

    override fun onShoppingListSelected(item: DailyMealModel, id : UUID?, controls: DailyMealAdapter.ViewHolder) {
        if(controls.mealSpinner.selectedItem.toString() == MealConstants.NO_SELECTION_MEAL){
            Snackbar.make(saveMeal, "No meal selected", Snackbar.LENGTH_LONG).show()
            return
        }
        AlertDialog.Builder(requireContext())
            .setMessage("Add ingredients to shopping list?")
            .setPositiveButton("Yes"){ dialog, id ->
                Toast.makeText(context, "Added to shopping list (not really)", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){dialog, id ->
                dialog.dismiss()
            }.create()
            .show()
    }
}