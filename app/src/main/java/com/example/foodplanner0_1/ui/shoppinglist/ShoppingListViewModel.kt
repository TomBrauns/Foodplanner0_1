package com.example.foodplanner0_1.ui.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoppingListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "You can see your Shopping List here" //Actual input on the tab
    }
    val text: LiveData<String> = _text
}