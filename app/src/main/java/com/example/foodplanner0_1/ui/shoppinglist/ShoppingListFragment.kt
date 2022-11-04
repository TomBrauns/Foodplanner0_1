package com.example.foodplanner0_1.ui.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodplanner0_1.databinding.FragmentShoppinglistBinding

class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppinglistBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val shoppingListViewModel =
            ViewModelProvider(this).get(ShoppingListViewModel::class.java)

        _binding = FragmentShoppinglistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textShoppinglist
        shoppingListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
