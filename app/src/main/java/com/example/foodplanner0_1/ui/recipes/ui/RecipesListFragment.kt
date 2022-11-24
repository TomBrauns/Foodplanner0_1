package com.example.foodplanner0_1.ui.recipes.ui

// This Document references back to the fragment_recipes.xml
// This Document ALSO references the recipes_list_item.xml

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.foodplanner0_1.ui.recipes.data.RecipesListItem
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.viewmodel.RecipesNavigationViewModel

private const val TAG = "RecipesListFragment"

class RecipesListFragment : Fragment() {

    private lateinit var recipesListHeader: Button

    private lateinit var recipesListRecyclerView: RecyclerView
    private var adapter: RecipesAdapter? = null

    private lateinit var moreRecipesButton: Button

    // model created by hosting activity
    private val viewmodel: RecipesNavigationViewModel by activityViewModels()

    // required interface for communication with hosting activities
    interface Callbacks {
        fun onRecipesSelected(recipeId: String)
        fun onMoreRecipesButtonSelected()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_recipes, container, false)

        recipesListRecyclerView = view.findViewById(R.id.recipes_list_recycler_view) as RecyclerView
        recipesListRecyclerView.layoutManager = LinearLayoutManager(context)
        recipesListRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        updateUI()

        return view
    }

    private fun updateUI() {
        val recipes = viewmodel.getMyRecipes()
        adapter = RecipesAdapter(recipes)
        recipesListRecyclerView.adapter = adapter
    }

    private inner class RecipesHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var recipesListItem: RecipesListItem

        // Importing the texts from the R.id
        private val recipesLabel: TextView = itemView.findViewById(R.id.recipes_label)
        private val recipesDescriptionText: TextView = itemView.findViewById(R.id.recipes_description)
        private val recipesIngredientsText: TextView = itemView.findViewById(R.id.recipes_ingredients)

        private val recipesButton: Button = itemView.findViewById(R.id.recipes_button)
        init {
            recipesButton.setOnClickListener(this)
        }

        fun bind(recipesListItem: RecipesListItem) {
            this.recipesListItem = recipesListItem
            recipesLabel.text = this.recipesListItem.title


            // Actually inputting the text
            recipesDescriptionText.text = this.recipesListItem.description
            recipesIngredientsText.text = this.recipesListItem.ingredients

            // Following two lines colourate the Recipes
            // --> required because background colour
            // AND text colour is white
            val colorDrawable = ColorDrawable(Color.GRAY)
            itemView.background = colorDrawable
        }

        // Click handler for one recipe line item
        override fun onClick(v: View) {
            callbacks?.onRecipesSelected(recipesListItem.productId)
        }

    }

    private inner class RecipesAdapter(var logovidListItems: List<RecipesListItem>) :
        RecyclerView.Adapter<RecipesHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
            val itemView = layoutInflater.inflate(R.layout.recipes_list_item, parent, false)

            val layoutParams: ViewGroup.LayoutParams = itemView.getLayoutParams()
            itemView.setLayoutParams(layoutParams)

            return RecipesHolder(itemView)
        }

        override fun getItemCount() = logovidListItems.size

        override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
            val logovid = logovidListItems[position]
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams

            holder.itemView.layoutParams = params
            holder.bind(logovid)
        }
    }

}
