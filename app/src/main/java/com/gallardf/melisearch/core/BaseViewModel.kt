package com.gallardf.melisearch.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    protected val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>>
        get() = _snackBarText
}