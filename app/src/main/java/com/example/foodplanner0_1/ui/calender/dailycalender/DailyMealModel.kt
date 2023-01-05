package com.example.foodplanner0_1.ui.calender.dailycalender

import com.example.foodplanner0_1.R

class DailyMealModel (
    var mealName: String = "Breakfast",
    var mealIcon: Int = R.drawable.ic_breakfast_crossant,
    var mealSelected: String? = null,
    var meals: ArrayList<String>
    ){
}