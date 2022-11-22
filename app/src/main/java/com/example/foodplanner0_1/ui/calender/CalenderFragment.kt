package com.example.foodplanner0_1.ui.calender

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.example.foodplanner0_1.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalenderFragment : Fragment() {

    lateinit var buttonPanel : Button
    lateinit var monthlyCalender: CalendarView
    lateinit var fecha: TextView
    var year1: Int = 0
    var month1: Int = 0
    var day1: Int = 0

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        fecha= view.findViewById(R.id.text1)
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MMM yyyy")
        val today = formatter.format(time)
        fecha.text = today

        monthlyCalender= view.findViewById(R.id.monthly_calendar)
        monthlyCalender.setOnDateChangeListener { calendarView, i, i1, i2 ->
            year1 = i
            month1 = i1+1
            day1 = i2
            fecha.text = "$month1"
        }

        buttonPanel = view.findViewById(R.id.button2)
        buttonPanel.setOnClickListener{
            Toast.makeText(activity, "$year1", Toast.LENGTH_LONG).show()
        }

        return view
    }

}
