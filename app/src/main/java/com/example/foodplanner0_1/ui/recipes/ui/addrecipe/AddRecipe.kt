package com.example.foodplanner0_1.ui.recipes.ui.addrecipe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.foodplanner0_1.R
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.data.RecipeDatabase
import com.example.foodplanner0_1.ui.recipes.ui.RecipeListFragment
import kotlinx.coroutines.launch
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddRecipe.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRecipe : Fragment() {
    private lateinit var titleInput : EditText
    private lateinit var descriptionInput : EditText
    private lateinit var effortInput : EditText
    private lateinit var ingredientsInput : EditText
    private lateinit var stepsInput : EditText

    private lateinit var saveRecipe : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_recipe, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            returnToRecipes()
        }

        titleInput = view.findViewById(R.id.recipe_title)
        descriptionInput = view.findViewById(R.id.recipe_description)
        effortInput = view.findViewById(R.id.recipe_effort)

        ingredientsInput = view.findViewById(R.id.ingredients_input)
        ingredientsInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable) {}
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun onTextChanged(
                text: CharSequence,
                start: Int,
                lengthBefore: Int,
                lengthAfter: Int
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
                text: CharSequence,
                start: Int,
                lengthBefore: Int,
                lengthAfter: Int
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

        saveRecipe = view.findViewById(R.id.recipe_save)

        saveRecipe.setOnClickListener(){
            val validators = listOf(titleInput, descriptionInput, effortInput, ingredientsInput, stepsInput)
            var isValid = true
            validators.forEach(){
                if(it.text.isEmpty()){
                    it.error = "Field cannot be empty"
                    isValid = false
                }
            }

            if(isValid){
                val recipe = Recipe(UUID.randomUUID(),
                    titleInput.text.toString(),
                    descriptionInput.text.toString(),
                    effortInput.text.toString(),
                    ingredientsInput.text.toString().replace("•", ""),
                    stepsInput.text.toString().replace("•", ""))

                val room = RecipeDatabase.get()

                lifecycleScope.launch{
                    try{
                        room.recipeDao().addRecipe(recipe)
                        Toast.makeText(context, "Recipe added", Toast.LENGTH_LONG).show()
                        returnToRecipes()
                    }catch(e : java.lang.Exception){
                        Toast.makeText(context, "Couldn't add recipe", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }



        return view
    }

    private fun returnToRecipes() {
        //Toast.makeText(context, "Jejeje", Toast.LENGTH_SHORT).show()
        val recipeFragment = RecipeListFragment()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, recipeFragment)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddRecipe().apply {
                arguments = Bundle().apply {
                }
            }
    }
}