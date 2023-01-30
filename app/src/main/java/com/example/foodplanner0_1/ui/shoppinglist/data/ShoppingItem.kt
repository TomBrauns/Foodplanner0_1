package com.example.foodplanner0_1.ui.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ShoppingItem(
    @PrimaryKey val id: UUID,
    var item: String,
    //var imageFileURL: String? = null
)
