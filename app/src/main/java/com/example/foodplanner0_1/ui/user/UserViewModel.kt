package com.example.foodplanner0_1.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the User-tab" //Actual input on the tab
    }
    val text: LiveData<String> = _text
}