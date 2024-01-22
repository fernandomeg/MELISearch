package com.gallardf.melisearch.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    protected val _errorAlert = MutableLiveData<Event<String>>()
    val errorAlert: LiveData<Event<String>>
        get() = _errorAlert
}