package com.gallardf.melisearch.data.repository_impl

import com.gallardf.melisearch.core.Constants
import com.gallardf.melisearch.data.MercadoLibreApi
import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.data.datasource.Picture
import com.gallardf.melisearch.data.datasource.ProductDetailResponse
import com.gallardf.melisearch.data.datasource.ProductResponse
import com.gallardf.melisearch.domain.models.ProductDetailModel
import com.gallardf.melisearch.domain.models.ProductModel
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.domain.repository.Repository
import com.gallardf.melisearch.utils.map
import timber.log.Timber

import javax.inject.Inject

class MercadoLibreRepositoryImpl @Inject constructor(
    private val mercadoLibreApi: MercadoLibreApi,
) : MercadoLibreRepository, Repository() {

    override suspend fun getProductsByQuery(query: String): Resource<List<ProductModel>> {
        return networkCall {mercadoLibreApi.getProductsByQuery(query)}
            .map(
                onSuccess = {
                    Timber.tag(Constants.TAG).d(it.results.toDomainModel().toString())
                    Resource.Success(it.results.toDomainModel())
                },
                onError = {
                    Timber.e(it, it.message)
                    Resource.Error(it)
                }
            )
    }

    override suspend fun getProductDetail(id: String): Resource<ProductDetailModel> {
        return networkCall { mercadoLibreApi.getDetailsById(id) }
            .map(
                onSuccess = {
                    Timber.tag(Constants.TAG).d(it.toDomainModel().toString())
                    Resource.Success(it.toDomainModel())
                },
                onError = {
                    Timber.e(it,it.message)
                    Resource.Error(it)
                }
            )
    }

    override suspend fun getProductDescription(id: String): Resource<String> {
        return networkCall { mercadoLibreApi.getDescriptionById(id) }
            .map(
                onSuccess = {
                    Timber.tag(Constants.TAG).d(it.toString())
                    Resource.Success(it.text ?: "")
                },
                onError = {
                    Timber.e(it,it.message)
                    Resource.Error(it)
                }
            )
    }

    private fun ProductDetailResponse.toDomainModel(): ProductDetailModel = ProductDetailModel(
        id = id ?: "",
        title = title ?: "",
        acceptsMercadopago = acceptsMercadopago,
        condition = condition ?: "",
        shipping = shipping?.freeShipping ?: false,
        pictures = pictures?.toDomainImageModel(),
        price = price,
        secureThumbnail = secureThumbnail ?: "",
        generalAddress = ("${sellerAddress?.city?.name}, ${sellerAddress?.state?.name}")
    )

    private fun List<Picture?>.toDomainImageModel(): List<String> =
        map { it?.secureUrl ?: "" }

    private fun List<ProductResponse>.toDomainModel(): List<ProductModel> =
        map { it.toDomainModel() }

    private fun ProductResponse.toDomainModel(): ProductModel = ProductModel(
        acceptsMercadoPago = acceptsMercadoPago ?: false,
        id = id ?: "",
        imageUrl = thumbnail ?: "",
        price = price ?: 0.0f,
        title = title ?: ""
    )
}