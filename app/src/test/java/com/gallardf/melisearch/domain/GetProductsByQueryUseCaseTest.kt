package com.gallardf.melisearch.domain

import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.domain.models.ProductModel
import com.gallardf.melisearch.domain.usecases.GetProductsByQueryUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.IOException

class GetProductsByQueryUseCaseTest {

    /*** Arrange instances ***/
    private val repository = mock(MercadoLibreRepository::class.java)
    private val useCase = GetProductsByQueryUseCase(repository)
    private val testQuery = "Samsung"

    @Test
    fun `getProductsByName success should return Resource_Success`() {

        /*** Run Coroutine ***/
        runBlocking {
            `when`(repository.getProductsByQuery(testQuery)).thenReturn(

                /*** Simulate a SUCCESS Response ***/
                Resource.Success(
                    listOf(
                        ProductModel(
                            id = "666",
                            price = 5559.66f,
                            title = "Samsung Test",
                            imageUrl = "something",
                            acceptsMercadoPago = true
                        )
                    )
                )
            )

            /*** Execute case ***/
            val result = useCase.getProductsByName(testQuery)

            /*** Assert Response ***/
            assertEquals(
                Resource.Success(
                    listOf(
                        ProductModel(
                            id = "666",
                            price = 5559.66f,
                            title = "Samsung Test",
                            imageUrl = "something",
                            acceptsMercadoPago = true
                        )
                    )
                ), result
            )
        }//End CoroutineScope
    }//End Test

    @Test
    fun `getProductsByName no internet should return Resource_Error`() {

        val mockErrorMessage = "Connection unavailable"

        /*** Run Coroutine ***/
        runBlocking {
            `when`(repository.getProductsByQuery(testQuery)).thenReturn(
                /*** Simulate IOException ERROR Response related to Connectivity ***/
                Resource.Error(IOException(mockErrorMessage))
            )

            /*** Execute case ***/
            val result = useCase.getProductsByName(testQuery)
            val resultExpected: Resource<Throwable> = Resource.Error(Throwable(mockErrorMessage))

            /*** Assert that the Error is instance of Throwable Response ***/
            assertEquals(resultExpected.javaClass, result.javaClass)
        }
    }
}