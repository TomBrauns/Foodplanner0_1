package com.example.foodplanner0_1.ui.calender

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.foodplanner0_1.R

class CalenderFragment : Fragment() {

    lateinit var buttonPanel : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calender, container, false)
        buttonPanel = view.findViewById(R.id.button2)
        buttonPanel.setOnClickListener(){
            Toast.makeText(activity, "Button clicked!", Toast.LENGTH_LONG).show()
        }

        return view
    }

}
