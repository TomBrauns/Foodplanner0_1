package com.example.foodplanner0_1.ui.recipes.recipedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodplanner0_1.databinding.FragmentRecipeDetailBinding



class RecipeDetailFragment : Fragment() { }
/*
    private var _binding: FragmentRecipeDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recipeDetailViewModel =
            ViewModelProvider(this).get(RecipeDetailViewModel::class.java)

        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.recipeLabel
        recipeDetailViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
*/