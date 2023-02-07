package com.example.foodplanner0_1.ui.calender.weeklycalendar

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.calender.dailycalendar.DailyCalendar
import com.example.foodplanner0_1.ui.calender.dailycalendar.MealConstants
import com.example.foodplanner0_1.ui.calender.monthlycalendar.CalendarFragment
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeeklyCalendar : Fragment() {
    var mealsList = mutableListOf<DayMeal>()
    val room = RecipeDatabase.get()

    lateinit var daysNumber: ArrayList<TextView>
    lateinit var calendar: Calendar
    lateinit var monthText: TextView
    private lateinit var monthlyViewButton: ImageButton
    private val monthYearFormatter = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    val formatter2 = SimpleDateFormat("E.dd.MMM.yyyy", Locale.ENGLISH)
    private val nameWeekFormatter = SimpleDateFormat("EEEE", Locale.ENGLISH)
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

        val view = inflater.inflate(R.layout.fragment_weekly_calendar, container, false)
        weekController(view)
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun weekController(view: View) {
        daysNumber = ArrayList<TextView>()
        val days = listOf(
            R.id.sunNum,
            R.id.monNum,
            R.id.tueNum,
            R.id.wedNum,
            R.id.thurNum,
            R.id.friNum,
            R.id.satNum
        )

        days.forEach() {
            daysNumber.add(view.findViewById(it))
        }

        monthText = view.findViewById(R.id.monthWeek)
        backDate = view.findViewById(R.id.buttonBack)
        nextDate = view.findViewById(R.id.buttonNext)
        monthlyViewButton = view.findViewById(R.id.goToMonth)

        calendar = Calendar.getInstance()
        // Calendar set to first sunday
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
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

        monthlyViewButton.setOnClickListener {
            val newFragment = CalendarFragment.newInstance(
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR)
            )

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, newFragment)
                .addToBackStack(null)
                .commit()

        }
    }

    private fun updateWeek() {
        val itCalendar = calendar.clone() as Calendar
        mealsList.clear()
        adapter.notifyItemRangeRemoved(0, 7)
        //MealDatabase.initialize(requireContext())


        lifecycleScope.launch {
            daysNumber.forEach() {

                it.text = itCalendar.get(Calendar.DAY_OF_MONTH).toString()
                if (DateUtils.isToday(itCalendar.timeInMillis)) {
                    it.setTypeface(null, Typeface.BOLD)
                } else {
                    it.setTypeface(null, Typeface.NORMAL)
                }

                val mealDb = room.recipeDao().getMeal(
                    itCalendar.get(Calendar.DAY_OF_MONTH),
                    itCalendar.get(Calendar.MONTH),
                    itCalendar.get(Calendar.YEAR)
                )
                var meal: DayMeal

                Log.d(
                    "--MEAL--" + itCalendar.get(Calendar.DAY_OF_MONTH).toString(),
                    mealDb?.breakfast.toString()
                )

                if (mealDb == null) {
                    meal = DayMeal(
                        nameWeekFormatter.format(itCalendar.time) + " " + itCalendar.get(Calendar.DAY_OF_MONTH)
                            .toString(),
                        MealConstants.NO_MEAL_SELECTED,
                        MealConstants.NO_MEAL_SELECTED,
                        MealConstants.NO_MEAL_SELECTED
                    )
                } else {
                    meal = DayMeal(
                        nameWeekFormatter.format(itCalendar.time) + " " + itCalendar.get(Calendar.DAY_OF_MONTH)
                            .toString(),
                        mealDb.breakfastName ?: MealConstants.NO_MEAL_SELECTED,
                        mealDb.lunchName ?: MealConstants.NO_MEAL_SELECTED,
                        mealDb.dinnerName ?: MealConstants.NO_MEAL_SELECTED
                    )
                }

                mealsList.add(meal)
                adapter.notifyItemInserted(mealsList.size - 1)
                itCalendar.add(Calendar.DATE, 1)
            }
        }

        monthText.text = monthYearFormatter.format(calendar.time)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //fillMeals()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.mealsRecycle)
        recyclerView.layoutManager = layoutManager
        adapter = MealsAdapter(mealsList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : MealsAdapter.MealClickListener {
            override fun onClick(meal: DayMeal) {
                val day = meal.date.split(" ").last().toInt()
                val month = calendar.get(Calendar.MONTH)
                val year = calendar.get(Calendar.YEAR)

                val dailyViewFragment = DailyCalendar.newInstance(day, month, year)

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, dailyViewFragment)
                    .addToBackStack(null)
                    .commit()
                //Toast.makeText(activity, "hi ${day}", Toast.LENGTH_LONG).show()
            }
        })

        updateWeek()
    }


}