package com.example.foodplanner0_1.ui.recipes.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Recipe(
    @PrimaryKey val id: UUID,
    var title: String,
    var description: String = "",
    var effort: String = "",
    var ingredients: String = "",
    var steps: String = ""
    //var imageFileURL: String? = null
)
