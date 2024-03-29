package com.example.foodplanner0_1.ui.calender.dailycalendar

import com.example.foodplanner0_1.R
import java.util.*
import kotlin.collections.ArrayList

class DailyMealModel (
    var mealName: String = "Breakfast",
    var mealIcon: Int = R.drawable.ic_breakfast_crossant,
    var mealSelected: String = MealConstants.NO_SELECTION_MEAL,
    var meals: ArrayList<String>,
    var mealsId : ArrayList<UUID?>
    )