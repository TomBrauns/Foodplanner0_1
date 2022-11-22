package com.example.foodplanner0_1.ui.calender

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foodplanner0_1.R
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class WeeklyCalender : Fragment() {

    lateinit var month: TextView

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_calender, container, false)
        month = view.findViewById(R.id.monthWeek)

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MMM yyyy")
        val today = formatter.format(time)
        month.text = today


        return view
    }


}