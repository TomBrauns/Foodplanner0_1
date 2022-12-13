package com.example.foodplanner0_1.ui.calender.monthlycalender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalenderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This will be a Calender" //Actual input on the tab
    }
    val text: LiveData<String> = _text


    }