package com.example.foodplanner0_1.ui.calender

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import java.text.SimpleDateFormat
import java.util.*

class WeeklyCalender : Fragment() {

    lateinit var month: TextView
    private lateinit var adapter: MealsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weekly_calender, container, false)
        getMonth(view)
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun getMonth(view: View) {
        month = view.findViewById(R.id.monthWeek)
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MMM yyyy")
        val today = formatter.format(time)
        month.text = today
    }

    private fun fillMeals() {
        val meal1 = DayMeal(
            "Sunday 15",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal1)

        val meal2 = DayMeal(
            "Monday 16",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal2)

        val meal3 = DayMeal(
            "Tuesday 17",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal3)

        val meal4 = DayMeal(
            "Wednesday 16",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal4)
        val meal5 = DayMeal(
            "Thursday 17",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal5)

        val meal6 = DayMeal(
            "Friday 17",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal6)

        val meal7 = DayMeal(
            "Saturday 17",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal7)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillMeals()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.mealsRecycle)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MealsAdapter(mealsList)
        recyclerView.adapter = adapter

    }

}