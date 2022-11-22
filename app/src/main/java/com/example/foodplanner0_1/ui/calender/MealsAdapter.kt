package com.example.foodplanner0_1.ui.calender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.databinding.CardWeeklyBinding

class MealsAdapter (private val meals: List<DayMeal>)
    :RecyclerView.Adapter<MealsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardWeeklyBinding.inflate(from,parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.binmeal(meals[position])
    }

    override fun getItemCount(): Int = meals.size
}