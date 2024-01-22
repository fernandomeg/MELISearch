package com.gallardf.melisearch.domain.usecases

import com.gallardf.melisearch.core.Constants
import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.domain.models.ProductDetailModel
import com.gallardf.melisearch.utils.map
import timber.log.Timber
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: MercadoLibreRepository
) {

    suspend fun invoke(id:String): Resource<ProductDetailModel> = repository
        .getProductDetail(id).map(
            onSuccess = {
                Timber.tag(Constants.TAG).d(it.toString())
                Resource.Success(it)
            },
            onError = {
                Timber.e(it,it.message)
                Resource.Error(it)
            }
        )
}