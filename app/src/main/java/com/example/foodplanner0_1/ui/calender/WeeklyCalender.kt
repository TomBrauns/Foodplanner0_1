package com.example.foodplanner0_1.ui.calender

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class WeeklyCalender : Fragment()
{

    lateinit var monthText: TextView
    lateinit var sunNum: TextView
    lateinit var monNum: TextView
    lateinit var tueNum: TextView
    lateinit var wedNum: TextView
    lateinit var thurNum: TextView
    lateinit var friNum: TextView
    lateinit var satNum: TextView
    private lateinit var adapter: MealsAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var backDate: FloatingActionButton
    lateinit var nextDate: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weekly_calender, container, false)
        weekController(view)
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun weekController(view: View) {
        monthText = view.findViewById(R.id.monthWeek)
        backDate = view.findViewById(R.id.buttonBack)
        nextDate = view.findViewById(R.id.buttonNext)
        sunNum = view.findViewById(R.id.sunNum)
        monNum = view.findViewById(R.id.monNum)
        tueNum = view.findViewById(R.id.tueNum)
        wedNum = view.findViewById(R.id.wedNum)
        thurNum = view.findViewById(R.id.thurNum)
        friNum = view.findViewById(R.id.friNum)
        satNum = view.findViewById(R.id.satNum)


        val calendar = Calendar.getInstance();
        val time = calendar.time
        val formatter1 = SimpleDateFormat("MMM yyyy")
        val formatter2 = SimpleDateFormat("E.dd.MMM.yyyy")
        var month = formatter1.format(time)
        val today = formatter2.format(time)
        monthText.text = month

        backDate.setOnClickListener {
            calendar.add(Calendar.DATE, -7)
            val updateDate = calendar.time
            month = formatter1.format(updateDate)
            monthText.text = month
            Toast.makeText(activity, "$updateDate", Toast.LENGTH_LONG).show()
        }

        nextDate.setOnClickListener {
            calendar.add(Calendar.DATE, +7)
            val updateDate = calendar.time
            month = formatter1.format(updateDate)
            monthText.text = month
            Toast.makeText(activity, "$updateDate", Toast.LENGTH_LONG).show()
        }

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
            "Friday 17",
            "Cereal",
            "Meat",
            "Fruit",
        )
        mealsList.add(meal4)
        val meal5 = DayMeal(
            "Friday 17",
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
        adapter = MealsAdapter(mealsList)
        recyclerView.adapter = adapter

    }


}