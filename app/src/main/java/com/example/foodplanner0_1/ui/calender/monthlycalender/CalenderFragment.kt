package com.example.foodplanner0_1.ui.calender.monthlycalender

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.calender.weeklycalender.DayMeal
import com.example.foodplanner0_1.ui.calender.weeklycalender.MealsAdapter
import com.example.foodplanner0_1.ui.calender.weeklycalender.WeeklyCalender
import com.example.foodplanner0_1.ui.calender.weeklycalender.mealsList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


private const val MONTH_NUMBER = "month"
private const val YEAR_NUMBER = "year"

class CalenderFragment : Fragment() {

    lateinit var calendar : Calendar
    lateinit var monthText: TextView
    lateinit var weeklyViewButton : ImageButton
    val monthYearFormatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    lateinit var backDate: FloatingActionButton
    lateinit var nextDate: FloatingActionButton

    private var monthSelected: Int? = null
    private var yearSelected: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            monthSelected = it.getInt(MONTH_NUMBER)
            yearSelected = it.getInt(YEAR_NUMBER)
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calender, container, false)


        monthText = view.findViewById(R.id.monthName)
        backDate = view.findViewById(R.id.buttonBackMonth)
        nextDate = view.findViewById(R.id.buttonNextMonth)
        weeklyViewButton = view.findViewById(R.id.goToWeek)

        calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, monthSelected?: 0)
        calendar.set(Calendar.YEAR, yearSelected?: 2023)
        //Toast.makeText(context, "${monthSelected} - ${yearSelected} (${calendar.get(Calendar.DAY_OF_MONTH)})", Toast.LENGTH_LONG).show()

        // Calendar set to first day
        while(calendar.get(Calendar.DAY_OF_MONTH) != 1){
            calendar.add(Calendar.DAY_OF_WEEK, -1)
        }

        backDate.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateMonth()
        }

        nextDate.setOnClickListener {
            calendar.add(Calendar.MONTH, +1)
            updateMonth()
        }

        weeklyViewButton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, WeeklyCalender())
                .commit()

        }
        updateMonth()
        return view
    }

    private fun updateMonth(){
        val itCalendar = calendar.clone() as Calendar
        monthText.text = monthYearFormatter.format(calendar.time)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param position Position of the dog in ArrayList
         * @return A new instance of fragment ViewDogModel.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(month: Int, year: Int) =
            CalenderFragment().apply {
                arguments = Bundle().apply {
                    putInt(MONTH_NUMBER, month)
                    putInt(YEAR_NUMBER, year)
                }
            }
    }

}
