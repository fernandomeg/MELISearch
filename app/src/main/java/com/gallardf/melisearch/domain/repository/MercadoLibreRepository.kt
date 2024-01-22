package com.gallardf.melisearch.domain.repository

import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.models.ProductDetailModel
import com.gallardf.melisearch.domain.models.ProductModel

interface MercadoLibreRepository {

    suspend fun getProductsByQuery(query:String): Resource<List<ProductModel>>

    suspend fun getProductDetail(id:String): Resource<ProductDetailModel>

    suspend fun getProductDescription(id:String): Resource<String>

}