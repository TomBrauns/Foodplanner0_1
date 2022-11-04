package com.example.foodplanner0_1.ui.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "In here you can see your saved Recipes" //Actual input on the tab
    }
    val text: LiveData<String> = _text
}