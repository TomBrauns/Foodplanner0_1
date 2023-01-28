package com.example.foodplanner0_1.ui.calender.dailycalender

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import java.util.UUID

class DailyMealAdapter (
    val source : ArrayList<DailyMealModel>,
    val context : Context,
    val listener : OnMealListener
        ) : RecyclerView.Adapter<DailyMealAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealIcon : ImageView = itemView.findViewById(R.id.mealIcon)
        val mealName : TextView = itemView.findViewById(R.id.mealName)
        val mealSpinner : Spinner = itemView.findViewById(R.id.mealSpinner)
        val addShopping : ImageView = itemView.findViewById(R.id.mealAddShopping)
        val seeRecipe : ImageView = itemView.findViewById(R.id.mealSeeRecipe)
    }

    interface OnMealListener{
        fun onRecipeSelected(item: DailyMealModel, id: UUID?,controls: DailyMealAdapter.ViewHolder)
        fun onShoppingListSelected(item: DailyMealModel, id : UUID?, controls : DailyMealAdapter.ViewHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.meal_selection_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = source[position]
        holder.mealIcon.setImageResource(item.mealIcon)
        holder.mealName.text = item.mealName

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, item.meals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.mealSpinner.adapter = adapter

        holder.mealSpinner.setSelection(item.meals.indexOf(item.mealSelected))

        holder.addShopping.setOnClickListener(){
            val idx = holder.mealSpinner.selectedItemPosition
            listener.onShoppingListSelected(item,  item.mealsId[idx] ,holder)
        }

        holder.seeRecipe.setOnClickListener(){
            val idx = holder.mealSpinner.selectedItemPosition
            listener.onRecipeSelected(item, item.mealsId[idx] ,holder)
        }
    }

    override fun getItemCount(): Int {
        return source.size
    }
}