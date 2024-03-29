package com.example.foodplanner0_1.ui.recipes.ui.recipedetail

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.recipes.ui.RecipeListFragment
import com.example.foodplanner0_1.ui.recipes.ui.editrecipe.EditRecipe
import com.example.foodplanner0_1.ui.shoppinglist.data.ShoppingItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_UUID = "UUID"
private const val ARG_ORIGIN = "ORIGIN"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetail : Fragment() {
    private var uuid: String? = null
    private var origin: String? = null

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var effort: TextView
    private lateinit var ingredients: TextView
    private lateinit var steps: TextView
    private lateinit var editButton: FloatingActionButton
    private lateinit var shoppingButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uuid = it.getString(ARG_UUID)
            origin = it.getString(ARG_ORIGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        // Inflate the layout for this fragment
        title = view.findViewById(R.id.view_recipe_name)
        description = view.findViewById(R.id.view_recipe_description)
        effort = view.findViewById(R.id.view_recipe_effort)
        ingredients = view.findViewById(R.id.view_recipe_ingredients)
        steps = view.findViewById(R.id.view_recipe_steps)
        editButton = view.findViewById(R.id.edit_recipe_button)
        shoppingButton = view.findViewById(R.id.add_shopping_list_button)

        editButton.setOnClickListener {
            val recipeFragment = EditRecipe.newInstance(uuid!!, origin!!)

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
                .addToBackStack(null)
                .commit()
        }

        val room = RecipeDatabase.get()

        if (origin == "RECIPES") {
            //Toast.makeText(context, "GO BACK RECIPES", Toast.LENGTH_LONG).show()
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                val recipeFragment = RecipeListFragment()
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .commit()
            }
        }
// The following block handles the linebreaks and general design of the Recipes. Ingredients are listed as bulletinpoints
// And the Steps are numbered.
        lifecycleScope.launch {
            var recipe = room.recipeDao().getRecipe(UUID.fromString(uuid))
            title.text = recipe.title
            description.text = recipe.description
            effort.text = recipe.effort
            ingredients.text = "• " + recipe.ingredients.replace("\n", "\n• ")
            var stepsText = ""

            recipe.steps.split("\n").forEachIndexed { index, step ->
                stepsText += "" + (index + 1) + ". " + step + "\n"
            }
            steps.text = stepsText

        }

        shoppingButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("Add ingredients to shopping list?")
                .setPositiveButton("Yes") { dialog, idView ->
                    lifecycleScope.launch {
                        val recipe = room.recipeDao().getRecipe(UUID.fromString(uuid!!))
                        val items = recipe.ingredients.split("\n")
                        val addItems = ArrayList<ShoppingItem>()

                        for (item in items) {
                            val ingredient = item.trim()
                            if (ingredient.isEmpty()) {
                                continue
                            }

                            addItems.add(
                                ShoppingItem(
                                    UUID.randomUUID(),
                                    item.trim()
                                )
                            )
                        }

                        room.recipeDao().addShoppingCart(addItems)

                        Toast.makeText(
                            context,
                            addItems.size.toString() + " ingredients added to shopping list",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }.create()
                .show()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(uuid: String, origin: String) =
            RecipeDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_UUID, uuid)
                    putString(ARG_ORIGIN, origin)
                }
            }
    }
}