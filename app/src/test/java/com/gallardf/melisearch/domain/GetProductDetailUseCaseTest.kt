package com.gallardf.melisearch.domain

import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.domain.models.ProductDetailModel
import com.gallardf.melisearch.domain.usecases.GetProductDetailUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException

class GetProductDetailUseCaseTest{

    /*** Arrange instances ***/
    private val repository = Mockito.mock(MercadoLibreRepository::class.java)
    private val useCase = GetProductDetailUseCase(repository)
    private val testProductId = "id123"

    @Test
    fun `getProductDetail success should return Resource_Success`() {

        runBlocking {
            Mockito.`when`(repository.getProductDetail(testProductId)).thenReturn(

                /*** Simulate a SUCCESS Response ***/
                Resource.Success(
                    ProductDetailModel(
                        id = testProductId,
                        title = "Samsung",
                        acceptsMercadopago = false,
                        condition = "New Product",
                        shipping = false,
                        pictures = emptyList(),
                        price = 0.00f,
                        secureThumbnail = "imageUrl",
                        generalAddress = "address fake"
                    )
                )
            )

            /*** Execute case ***/
            val result = useCase.invoke(testProductId)

            /*** Assert Response ***/
            assertEquals(
                Resource.Success(
                    ProductDetailModel(
                        id = testProductId,
                        title = "Samsung",
                        acceptsMercadopago = false,
                        condition = "New Product",
                        shipping = false,
                        pictures = emptyList(),
                        price = 0.00f,
                        secureThumbnail = "imageUrl",
                        generalAddress = "address fake"
                    )
                ), result
            )
        }//End CoroutineScope
    }//End Test

    @Test
    fun `getProductDetail no internet should return Resource_Error`() {

        val mockErrorMessage = "Connection unavailable"

        /*** Run Coroutine ***/
        runBlocking {
            Mockito.`when`(repository.getProductDetail(testProductId)).thenReturn(
                /*** Simulate IOException ERROR Response related to Connectivity ***/
                Resource.Error(IOException(mockErrorMessage))
            )

            /*** Execute case ***/
            val result = useCase.invoke(testProductId)
            val resultExpected: Resource<Throwable> = Resource.Error(Throwable(mockErrorMessage))

            /*** Assert that the Error is instance of Throwable Response ***/
            assertEquals(resultExpected.javaClass, result.javaClass)
        }
    }

}