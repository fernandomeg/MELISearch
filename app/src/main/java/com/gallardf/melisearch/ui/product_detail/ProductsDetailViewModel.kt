package com.gallardf.melisearch.ui.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gallardf.melisearch.core.BaseViewModel
import com.gallardf.melisearch.core.Event
import com.gallardf.melisearch.domain.usecases.GetProductDescriptionUseCase
import com.gallardf.melisearch.domain.usecases.GetProductDetailUseCase
import com.gallardf.melisearch.domain.models.ProductDetailModel
import com.gallardf.melisearch.utils.onErrorResource
import com.gallardf.melisearch.utils.onSuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase
): BaseViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private var _productDetail = MutableLiveData<ProductDetailModel>()
    val productDetail: LiveData<ProductDetailModel>
        get() = _productDetail


    private var _productDescription = MutableLiveData<String>()
    val productDescription: LiveData<String>
        get() = _productDescription

    fun getDetail(id:String) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            getProductDetailUseCase.invoke(id)
                .onSuccessResource {
                    _loading.postValue(false)
                    _productDetail.postValue(it)
                }
                .onErrorResource {
                    _loading.postValue(false)
                    _snackBarText.postValue(Event(it.message!!))
                }
        }

    fun getDescription(id:String) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            getProductDescriptionUseCase.invoke(id)
                .onSuccessResource {
                    _loading.postValue(false)
                    _productDescription.postValue(it)
                }
                .onErrorResource {
                    _loading.postValue(false)
                    _snackBarText.postValue(Event(it.message!!))
                }
        }
}