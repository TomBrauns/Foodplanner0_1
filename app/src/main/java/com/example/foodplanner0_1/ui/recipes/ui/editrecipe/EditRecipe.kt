package com.example.foodplanner0_1.ui.recipes.ui.editrecipe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.R.id
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.recipes.ui.recipedetail.RecipeDetail
import kotlinx.coroutines.launch
import java.util.*

private const val ARG_UUID = "UUID_"
private const val ARG_ORIGIN = "ORIGIN_"

class EditRecipe : Fragment() {
    private var uuid: String? = null
    private var origin: String? = null

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var effortInput: EditText
    private lateinit var ingredientsInput: EditText
    private lateinit var stepsInput: EditText

    private lateinit var saveRecipe: Button
    private lateinit var deleteRecipe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uuid = it.getString(ARG_UUID)
            origin = it.getString(ARG_ORIGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_recipe, container, false)

        titleInput = view.findViewById(R.id.recipe_title)
        descriptionInput = view.findViewById(R.id.recipe_description)
        effortInput = view.findViewById(R.id.recipe_effort)

        ingredientsInput = view.findViewById(R.id.ingredients_input)
        ingredientsInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable) {}
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun onTextChanged(
                text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int
            ) {
                var text = text
                if (lengthAfter > lengthBefore) {
                    if (text.toString().length == 1) {
                        text = "• $text"
                        ingredientsInput.setText(text)
                        ingredientsInput.setSelection(ingredientsInput.text.length)
                    }
                    if (text.toString().endsWith("\n")) {
                        text = text.toString().replace("\n", "\n• ")
                        text = text.toString().replace("• •", "•")
                        ingredientsInput.setText(text)
                        ingredientsInput.setSelection(ingredientsInput.text.length)
                    }
                }
            }
        })

        stepsInput = view.findViewById(R.id.steps_input)
        stepsInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable) {}
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun onTextChanged(
                text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int
            ) {
                var text = text
                if (lengthAfter > lengthBefore) {
                    if (text.toString().length == 1) {
                        text = "• $text"
                        stepsInput.setText(text)
                        stepsInput.setSelection(stepsInput.text.length)
                    }
                    if (text.toString().endsWith("\n")) {
                        text = text.toString().replace("\n", "\n• ")
                        text = text.toString().replace("• •", "•")
                        stepsInput.setText(text)
                        stepsInput.setSelection(stepsInput.text.length)
                    }
                }
            }
        })

        if (origin == "RECIPES") {
            //Toast.makeText(context, "GO BACK RECIPES", Toast.LENGTH_LONG).show()
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                val recipeFragment = RecipeDetail.newInstance(uuid.toString(), origin!!)

                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
                    .addToBackStack(null).setReorderingAllowed(true).commit()
            }
        }

        val room = RecipeDatabase.get()
        lifecycleScope.launch {
            var recipe = room.recipeDao().getRecipe(UUID.fromString(uuid))
            titleInput.setText(recipe.title)
            descriptionInput.setText(recipe.description)
            effortInput.setText(recipe.effort)
            ingredientsInput.setText("•" + recipe.ingredients.replace("\n", "\n•"))
            ingredientsInput.setSelection(ingredientsInput.text.length)
            stepsInput.setText("•" + recipe.steps.replace("\n", "\n•"))
            stepsInput.setSelection(stepsInput.text.length)
        }

        saveRecipe = view.findViewById(R.id.recipe_save)
        saveRecipe.setOnClickListener() {
            val validators =
                listOf(titleInput, descriptionInput, effortInput, ingredientsInput, stepsInput)
            var isValid = true
            validators.forEach() {
                if (it.text.isEmpty()) {
                    it.error = "Field cannot be empty"
                    isValid = false
                }
            }

            if (isValid) {

                lifecycleScope.launch {
                    try {
                        room.recipeDao().updateRecipe(
                            UUID.fromString(uuid),
                            titleInput.text.toString(),
                            descriptionInput.text.toString(),
                            effortInput.text.toString(),
                            ingredientsInput.text.toString().replace("•", ""),
                            stepsInput.text.toString().replace("•", "")
                        )

                        Toast.makeText(context, "Recipe edited", Toast.LENGTH_LONG).show()
                        if (origin == "RECIPES") {
                            val recipeFragment = RecipeDetail.newInstance(uuid.toString(), origin!!)

                            parentFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
                                .addToBackStack(null).setReorderingAllowed(true).commit()
                        } else {
                            requireActivity().onBackPressed()
                        }

                    } catch (e: java.lang.Exception) {
                        Toast.makeText(context, "Couldn't update recipe", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        deleteRecipe = view.findViewById(R.id.recipe_delete)
        deleteRecipe.setOnClickListener() {
            lifecycleScope.launch {
                try {
                    room.recipeDao().deleteRecipe(UUID.fromString(uuid))
                } catch (e: java.lang.Exception) {
                    Toast.makeText(context, "Recipe deletion failed!", Toast.LENGTH_LONG).show()
                }

            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(uuid: String, origin: String) = EditRecipe().apply {
            arguments = Bundle().apply {
                putString(ARG_UUID, uuid)
                putString(ARG_ORIGIN, origin)
            }
        }
    }

}