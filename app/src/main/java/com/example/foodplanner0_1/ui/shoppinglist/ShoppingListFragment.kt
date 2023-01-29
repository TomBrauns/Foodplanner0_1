package com.example.foodplanner0_1.ui.shoppinglist

import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

class ShoppingListFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var itemsRecyclerView : RecyclerView
    private lateinit var newItemButton : FloatingActionButton

    val room = RecipeDatabase.get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_shoppinglist, container, false)

        itemsRecyclerView = view.findViewById(R.id.shopping_list_recycler)
        newItemButton = view.findViewById(R.id.shopping_list_button)

        newItemButton.setOnClickListener(){
            val input = EditText(context)

            val margin = dpToPx(100)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(30, 0, 30, 0)
            input.layoutParams = lp
            input.gravity = View.TEXT_ALIGNMENT_GRAVITY
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.hint = "Type the item to add"

            AlertDialog.Builder(context)
                .setTitle("Add item")
                .setMessage("Add an item to the shopping list")
                .setView(input)
                .setPositiveButton("Add") { dialog, id ->
                    lifecycleScope.launch {
                        room.recipeDao()
                            .addShoppingItem(ShoppingItem(UUID.randomUUID(), input.text.toString()))
                        Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show()
                        updateShoppingList()
                    }
                }.show()
        }

        return view
    }

    fun updateShoppingList(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().getDisplayMetrics().density).toInt()
    }
}
