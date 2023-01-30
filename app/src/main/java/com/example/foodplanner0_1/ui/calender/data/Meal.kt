package com.example.foodplanner0_1.ui.calender.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Meal(
    @PrimaryKey val id: UUID,
    var day: Int,
    var month: Int,
    var year: Int,
    var breakfast : UUID?,
    var lunch : UUID?,
    var dinner : UUID?
)
