package com.example.foodplanner0_1.ui.calender.dailycalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.foodplanner0_1.MainActivity
import com.example.foodplanner0_1.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private const val DAY_PARAM = "dayParam"
private const val MONTH_PARAM = "monthParam"
private const val YEAR_PARAM = "yearParam"

class DailyCalender : Fragment() {
    private var day : Int? = null
    private var month : Int? = null
    private var year : Int? = null
    private lateinit var calendar : Calendar

    private lateinit var dayNumber : TextView
    private lateinit var monthYear : TextView
    private lateinit var saveMeal : Button
    private lateinit var breakfastSpinner : Spinner

    private val monthYearFormatter = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val nameWeekFormatter = SimpleDateFormat("EEEE", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getInt(DAY_PARAM)
            month = it.getInt(MONTH_PARAM)
            year = it.getInt(YEAR_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_calender, container, false)

        dayNumber = view.findViewById(R.id.dayNumberDaily)
        monthYear = view.findViewById(R.id.monthYearDaily)
        saveMeal = view.findViewById(R.id.saveDaily)
        breakfastSpinner = view.findViewById(R.id.breakfastSpinner)

        calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day?: 1)
        calendar.set(Calendar.MONTH, month?: 1)
        calendar.set(Calendar.YEAR, year?: 2023)

        dayNumber.text = nameWeekFormatter.format(calendar.time) + " " + calendar.get(Calendar.DAY_OF_MONTH).toString()
        monthYear.text = monthYearFormatter.format(calendar.time)

        saveMeal.setOnClickListener{
            //(activity as MainActivity).clickBottomItem(0)
            Snackbar.make(view, "Meals updated", Snackbar.LENGTH_LONG).show()
            Executors.newSingleThreadScheduledExecutor().schedule({
                parentFragmentManager.popBackStack()
                }, 600, TimeUnit.MILLISECONDS)
        }

        val foods = ArrayList<String>()
        for(i in 1..200){
            foods.add("Food " + i)
        }
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, foods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        breakfastSpinner.adapter = adapter

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(day: Int, month: Int, year: Int) =
            DailyCalender().apply {
                arguments = Bundle().apply {
                    putInt(DAY_PARAM, day)
                    putInt(MONTH_PARAM, month)
                    putInt(YEAR_PARAM, year)
                }
            }
    }
}