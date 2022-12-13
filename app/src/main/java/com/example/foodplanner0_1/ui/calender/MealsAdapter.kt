package com.example.foodplanner0_1.ui.calender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.CardWeeklyBinding

class MealsAdapter (
    private val meals: List<DayMeal>
)
    :RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardWeeklyBinding.inflate(from, parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.binMeal(meals[position])
    }

    override fun getItemCount(): Int = meals.size


    class MealsViewHolder(
        private val cardCellBinding: CardWeeklyBinding,
    ) : RecyclerView.ViewHolder(cardCellBinding.root) {

        fun binMeal(meal: DayMeal) {
            cardCellBinding.date.text = meal.date
            cardCellBinding.breakfast.text = meal.breakfast
            cardCellBinding.lunch.text = meal.lunch
            cardCellBinding.dinner.text = meal.dinner

            cardCellBinding.cardView.setOnClickListener {
            }

        }
    }
}