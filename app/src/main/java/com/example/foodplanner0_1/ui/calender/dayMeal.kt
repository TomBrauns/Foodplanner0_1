package com.example.foodplanner0_1.ui.calender

var mealsList = mutableListOf<DayMeal>()

class DayMeal (
    var date: String,
    var breakfast: String,
    var lunch: String,
    var dinner: String,
    var id: Int? = mealsList.size
)

