package com.example.foodplanner0_1.ui.calender.weeklycalender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.CardWeeklyBinding


class MealsAdapter (
    private val meals: List<DayMeal>
)
    :RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {

    private var clickListener: MealClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardWeeklyBinding.inflate(from, parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.binMeal(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    fun setOnItemClickListener(mealClickListener: MealClickListener) {
        this.clickListener = mealClickListener
    }


    inner class MealsViewHolder(
        private val cardCellBinding: CardWeeklyBinding
    ) : RecyclerView.ViewHolder(cardCellBinding.root){

        fun binMeal(meal: DayMeal) {
            cardCellBinding.date.text = meal.date
            cardCellBinding.breakfast.text = meal.breakfast
            cardCellBinding.lunch.text = meal.lunch
            cardCellBinding.dinner.text = meal.dinner

            cardCellBinding.cardView.setOnClickListener{
                clickListener?.onClick(meal)
            }

        }
    }

    interface MealClickListener {
        fun onClick(meal: DayMeal)
    }
}