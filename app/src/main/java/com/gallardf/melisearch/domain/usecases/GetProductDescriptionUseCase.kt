package com.gallardf.melisearch.domain.usecases

import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.utils.map
import timber.log.Timber
import javax.inject.Inject

class GetProductDescriptionUseCase @Inject constructor(
    private val repository: MercadoLibreRepository
) {

    suspend fun invoke(id:String): Resource<String> = repository
        .getProductDescription(id).map(
            onSuccess = {
                Timber.d(it)
                Resource.Success(it)
            },
            onError = {
                Timber.e(it,it.message)
                Resource.Error(it)
            }
        )

}