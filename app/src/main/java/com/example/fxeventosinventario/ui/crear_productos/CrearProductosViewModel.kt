package com.example.fxeventosinventario.ui.crear_productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CrearProductosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is crear_productos Fragment"
    }
    val text: LiveData<String> = _text
}