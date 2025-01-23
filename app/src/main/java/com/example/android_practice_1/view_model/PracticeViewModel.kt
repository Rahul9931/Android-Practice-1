package com.example.android_practice_1.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PracticeViewModel:ViewModel() {
    private val factLiveDataObject = MutableLiveData("This is a fact")

    val factLiveData:LiveData<String> get() = factLiveDataObject
    fun updateFact(){
        factLiveDataObject.value = "Another fact"
    }
}