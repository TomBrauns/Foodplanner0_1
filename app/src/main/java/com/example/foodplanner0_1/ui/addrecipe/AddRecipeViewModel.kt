package com.example.foodplanner0_1.ui.addrecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddRecipeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This will Open a Popup to Add Recipes to specific days"
    }
    val text: LiveData<String> = _text
}