package com.gallardf.melisearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gallardf.melisearch.core.BaseViewModel
import com.gallardf.melisearch.core.Event
import com.gallardf.melisearch.domain.usecases.GetProductsByQueryUseCase
import com.gallardf.melisearch.domain.models.ProductModel
import com.gallardf.melisearch.utils.onErrorResource
import com.gallardf.melisearch.utils.onSuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase
): BaseViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>>
        get() = _products

    fun searchProductsByQuery(query:String) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            getProductsByQueryUseCase.getProductsByName(query)
                .onSuccessResource {
                    _loading.postValue(false)
                    _products.postValue(it)
                }
                .onErrorResource {
                    _loading.postValue(false)
                    _snackBarText.postValue(Event(it.message!!))
                }
        }
}