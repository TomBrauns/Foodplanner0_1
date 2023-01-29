package com.example.foodplanner0_1.ui.calender.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_NAME = "meal-database"

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var instance: MealDatabase? = null
        fun initialize(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext, MealDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    ./** addCallback(
                    RecipePreset(context)
                    ).fallbackToDestructiveMigration() .*/build()
            }
        }

        fun get(): MealDatabase {
            return instance
                ?: throw IllegalStateException("MealDatabase must be initialized")
        }
    }

}
