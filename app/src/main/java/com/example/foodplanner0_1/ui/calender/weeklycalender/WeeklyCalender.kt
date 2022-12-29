package com.example.foodplanner0_1.ui.calender.weeklycalender

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class WeeklyCalender : Fragment()
{

    lateinit var daysNumber: ArrayList<TextView>
    lateinit var calendar : Calendar
    lateinit var monthText: TextView
    val formatter1 = SimpleDateFormat("MMM yyyy")
    val formatter2 = SimpleDateFormat("E.dd.MMM.yyyy")
    val nameWeekFormatter = SimpleDateFormat("EEEE", Locale.ENGLISH)
    private lateinit var adapter: MealsAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var backDate: FloatingActionButton
    lateinit var nextDate: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userViewModel =
            ViewModelProvider(this).get(WeeklyViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_weekly_calender, container, false)
        weekController(view)
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun weekController(view: View) {
        daysNumber = ArrayList<TextView>()
        val days = listOf(R.id.sunNum, R.id.monNum, R.id.tueNum, R.id.wedNum, R.id.thurNum, R.id.friNum, R.id.satNum)

        days.forEach(){
            daysNumber.add(view.findViewById(it))
        }

        monthText = view.findViewById(R.id.monthWeek)
        backDate = view.findViewById(R.id.buttonBack)
        nextDate = view.findViewById(R.id.buttonNext)

        calendar = Calendar.getInstance()
        // Calendar set to first sunday
        while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
            calendar.add(Calendar.DAY_OF_WEEK, -1)
        }

        backDate.setOnClickListener {
            calendar.add(Calendar.DATE, -7)
            updateWeek()
        }

        nextDate.setOnClickListener {
            calendar.add(Calendar.DATE, +7)
            updateWeek()
        }

    }

    private fun updateWeek(){
        val itCalendar = calendar.clone() as Calendar
        mealsList.clear()
        adapter.notifyItemRangeRemoved(0, 7)
        daysNumber.forEach(){
            it.text = itCalendar.get(Calendar.DAY_OF_MONTH).toString()
            if(DateUtils.isToday(itCalendar.timeInMillis)){
                it.setTypeface(null, Typeface.BOLD)
            }else{
                it.setTypeface(null, Typeface.NORMAL)
            }
            val meal = DayMeal(
                nameWeekFormatter.format(itCalendar.time) + " " + itCalendar.get(Calendar.DAY_OF_MONTH).toString(),
                "Breakfast","Lunch","Dinner"
            )
            mealsList.add(meal)
            itCalendar.add(Calendar.DATE, 1)
        }
        adapter.notifyItemRangeInserted(0, 7)
        monthText.text = formatter1.format(calendar.time)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //fillMeals()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.mealsRecycle)
        recyclerView.layoutManager = layoutManager
        adapter = MealsAdapter(mealsList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MealsAdapter.MealClickListener{
            override fun onClick(meal: DayMeal) {
                Toast.makeText(activity, "hi", Toast.LENGTH_LONG).show()
            }
        })

        updateWeek()
    }


}