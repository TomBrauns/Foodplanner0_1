package com.example.foodplanner0_1.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.foodplanner0_1.databinding.FragmentCalenderBinding

class CalenderFragment : Fragment() {

    private var _binding: FragmentCalenderBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calenderViewModel =
            ViewModelProvider(this).get(CalenderViewModel::class.java)

        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCalender
        calenderViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
