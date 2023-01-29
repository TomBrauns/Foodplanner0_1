package com.example.foodplanner0_1.ui.shoppinglist.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import kotlinx.coroutines.CoroutineScope

class ShoppingItemAdapter(
    var source: ArrayList<ShoppingList>,
    var context: Context,
    var listener: OnItemListener
    ): RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder>(){
        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val itemText : TextView = itemView.findViewById(R.id.item_description)
            val itemQty : TextView = itemView.findViewById(R.id.item_qty)
            val addButton : TextView = itemView.findViewById(R.id.item_add_qty)
            val subButton : TextView = itemView.findViewById(R.id.item_sub_qty)
        }

    interface OnItemListener{
        fun onAddItem(description : String)
        fun onSubItem(description : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_shoppinglist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = source[position]
        holder.itemText.text = item.item
        holder.itemQty.text = item.count.toString()

        holder.addButton.setOnClickListener{
            listener.onAddItem(item.item)
        }

        holder.subButton.setOnClickListener{
            listener.onSubItem(item.item)
        }

    }

    override fun getItemCount(): Int {
        return source.size
    }


}