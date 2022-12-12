package com.example.foodplanner0_1.ui.calender
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.CardWeeklyBinding


class MealsViewHolder (
    private val cardCellBinding: CardWeeklyBinding,
    private val clickListener: MealClickListener
        )
    :RecyclerView.ViewHolder(cardCellBinding.root)
{

    fun binMeal(meal: DayMeal) {
      cardCellBinding.date.text = meal.date
      cardCellBinding.breakfast.text = meal.breakfast
      cardCellBinding.lunch.text = meal.lunch
      cardCellBinding.dinner.text = meal.dinner

      cardCellBinding.cardView.setOnClickListener{
          clickListener.onClick(meal)
      }

      }
    }