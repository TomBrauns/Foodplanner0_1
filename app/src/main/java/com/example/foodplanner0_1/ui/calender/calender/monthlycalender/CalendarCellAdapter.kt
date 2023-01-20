package com.example.foodplanner0_1.ui.calender.monthlycalender

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import java.util.*
import kotlin.collections.ArrayList


class CalendarCellAdapter(
    var source : ArrayList<DayCellModel>,
    var context : Context,
    var listener : OnCellListener
) : RecyclerView.Adapter<CalendarCellAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayNumber: TextView = itemView.findViewById(R.id.cellDayText)
        val breakfastIcon: ImageView = itemView.findViewById(R.id.breakfastCell)
        val lunchIcon: ImageView = itemView.findViewById(R.id.lunchCell)
        val dinnerIcon: ImageView = itemView.findViewById(R.id.dinnerCell)
    }

    interface OnCellListener{
        fun onItemClick(item : DayCellModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.1666).toInt()
        return ViewHolder(view)
    }

    private fun setIconColor(icon : ImageView, isSet : Boolean){
        icon.setColorFilter(
            ContextCompat.getColor(
                context,
                if(isSet) R.color.teal_700 else  R.color.grey_light
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = source[position]
        if(item.day == null){
            holder.dayNumber.text = ""
            holder.breakfastIcon.visibility = View.INVISIBLE
            holder.lunchIcon.visibility = View.INVISIBLE
            holder.dinnerIcon.visibility = View.INVISIBLE
            return
        }

        holder.itemView.setOnClickListener{
            listener.onItemClick(item)
        }

        holder.dayNumber.text = item.day!!.get(Calendar.DAY_OF_MONTH).toString()
        setIconColor(holder.breakfastIcon, item.hasBreakfast)
        setIconColor(holder.lunchIcon, item.hasLunch)
        setIconColor(holder.dinnerIcon, item.hasDinner)
    }

    override fun getItemCount(): Int {
        return source.size
    }
}