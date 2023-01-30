package com.example.foodplanner0_1.ui.shoppinglist

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingItem
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingItemAdapter
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class ShoppingListFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var itemsRecyclerView : RecyclerView
    private lateinit var adapter : ShoppingItemAdapter
    private lateinit var newItemButton : FloatingActionButton

    val room = RecipeDatabase.get()

    val listener : ShoppingItemAdapter.OnItemListener = object :
        ShoppingItemAdapter.OnItemListener {
        override fun onAddItem(description: String) {
            lifecycleScope.launch{
                room.recipeDao().addShoppingItem(ShoppingItem(UUID.randomUUID(), description))
                Snackbar.make(itemsRecyclerView, description + " added", Snackbar.LENGTH_SHORT).show()
                updateShoppingList()
            }
        }

        override fun onSubItem(description: String) {
            lifecycleScope.launch{
                room.recipeDao().deleteOne(description)
                Snackbar.make(itemsRecyclerView, description + " removed", Snackbar.LENGTH_SHORT).show()
                updateShoppingList()
            }
        }

    }

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

        updateShoppingList()
        return view
    }

    private fun updateShoppingList(){
        lifecycleScope.launch{
            val listItems = ArrayList<ShoppingList>(room.recipeDao().getShoppingList())

            val layoutManager = LinearLayoutManager(context)
            itemsRecyclerView.layoutManager = layoutManager

            adapter = ShoppingItemAdapter(listItems, requireContext(), listener)
            itemsRecyclerView.adapter = adapter
            adapter.notifyItemRangeInserted(0, listItems.size)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
