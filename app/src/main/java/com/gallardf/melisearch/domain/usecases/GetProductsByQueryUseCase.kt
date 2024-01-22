package com.gallardf.melisearch.domain.usecases

import com.gallardf.melisearch.core.Constants
import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.domain.models.ProductModel
import com.gallardf.melisearch.utils.map
import timber.log.Timber
import javax.inject.Inject

class GetProductsByQueryUseCase @Inject constructor(
    private val repository: MercadoLibreRepository
) {

    suspend fun getProductsByName(name:String): Resource<List<ProductModel>> {
        return repository.getProductsByQuery(name).map(
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
}