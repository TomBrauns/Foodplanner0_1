package com.example.foodplanner0_1.ui.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodplanner0_1.databinding.FragmentRecipesBinding
import com.example.foodplanner0_1.ui.recipes.data.Recipe
import com.example.foodplanner0_1.ui.recipes.model.RecipeRepository
import com.example.foodplanner0_1.ui.recipes.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch
import java.util.*


private const val TAG = "RecipeListFragment"

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access Binding (because it is null. Is the view visible?"
        }

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        binding.recipesListRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                recipeViewModel.recipes.collect { recipes ->
                    binding.recipesListRecyclerView.adapter =
                        RecipeListAdapter(recipes)
                        { recipeId ->
                            findNavController().navigate(
                                RecipeListFragmentDirections.showRecipeDetail(recipeId)
                            )
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}