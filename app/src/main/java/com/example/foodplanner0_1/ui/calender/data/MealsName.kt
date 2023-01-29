package com.example.foodplanner0_1.ui.calender.data

import java.util.*

class MealsName (
    var id : UUID,
    var day: Int,
    var month : Int,
    var year : Int,
    var breakfast : UUID?,
    var lunch : UUID?,
    var dinner : UUID?,
    var breakfastName : String?,
    var lunchName : String?,
    var dinnerName : String?){
}