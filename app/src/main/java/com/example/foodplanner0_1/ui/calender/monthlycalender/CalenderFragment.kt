package com.example.foodplanner0_1.ui.calender.monthlycalender

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.calender.dailycalender.DailyCalender
import com.example.foodplanner0_1.ui.calender.data.Meal
import com.example.foodplanner0_1.ui.calender.weeklycalender.WeeklyCalender
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


private const val MONTH_NUMBER = "month"
private const val YEAR_NUMBER = "year"

class CalenderFragment : Fragment(), CalendarCellAdapter.OnCellListener {

    lateinit var calendar : Calendar
    lateinit var monthText: TextView
    lateinit var weeklyViewButton : ImageButton
    private val monthYearFormatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    private lateinit var backDate: FloatingActionButton
    private lateinit var nextDate: FloatingActionButton
    private lateinit var calendarRecyclerView: RecyclerView


    private var monthSelected: Int? = null
    private var yearSelected: Int? = null
    val room = RecipeDatabase.get()

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
        calendarRecyclerView = view.findViewById(R.id.monthElements)

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
                .addToBackStack(null)
                .commit()

        }
        updateMonth()
        return view
    }

    private fun updateMonth(){
        monthText.text = monthYearFormatter.format(calendar.time)
        lifecycleScope.launch{
            val monthMeals = room.recipeDao().getMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
            for(meal in monthMeals){
                Log.d("MONTH MEAL", meal.breakfast.toString() + "   " + meal.day.toString())
            }
            val days = getDays(monthMeals)
            val adapter = CalendarCellAdapter(days, requireContext(), this@CalenderFragment)
            val layoutManager = GridLayoutManager(context, 7)
            calendarRecyclerView.layoutManager = layoutManager
            calendarRecyclerView.adapter = adapter
        }
    }

    private fun getDays(meals : List<Meal>) : ArrayList<DayCellModel> {
        val days = ArrayList<DayCellModel>()

        val itCalendar = calendar.clone() as Calendar
        val daysInMonth = itCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        var dayOfWeek = itCalendar.get(Calendar.DAY_OF_WEEK)

        if (dayOfWeek == 7) {
            dayOfWeek = 0
        }

        //Toast.makeText(context, "${daysInMonth} - ${dayOfWeek} - ${itCalendar.get(Calendar.DAY_OF_MONTH)}", Toast.LENGTH_LONG).show()

        for(i in 1..42){
            if(i < dayOfWeek || i >= daysInMonth + dayOfWeek){
                days.add(
                    DayCellModel(false,  false, false, null)
                )
            }else{
                days.add(
                    DayCellModel(
                        meals.any{ it -> it.day == itCalendar.get(Calendar.DAY_OF_MONTH) && it.breakfast != null },
                        meals.any{ it -> it.day == itCalendar.get(Calendar.DAY_OF_MONTH) && it.lunch != null },
                        meals.any{ it -> it.day == itCalendar.get(Calendar.DAY_OF_MONTH) && it.dinner != null },
                        itCalendar.clone() as Calendar)
                )
                itCalendar.add(Calendar.DATE, 1)
            }
        }

        return days
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

    override fun onItemClick(item: DayCellModel) {
        //Toast.makeText(context, "${SimpleDateFormat("E.dd.MMM.yyyy", Locale.ENGLISH).format(item.day!!.time)} selected", Toast.LENGTH_LONG).show()
        val day = item.day!!.get(Calendar.DAY_OF_MONTH)
        val month = item.day!!.get(Calendar.MONTH)
        val year = item.day!!.get(Calendar.YEAR)

        val dailyViewFragment = DailyCalender.newInstance(day, month, year)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, dailyViewFragment)
            .addToBackStack(null)
            .commit()
    }

}
