package com.gallardf.melisearch.domain

import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.domain.usecases.GetProductDescriptionUseCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException

class GetProductDescriptionUseCaseTest{
    /*** Arrange instances ***/
    private val repository = Mockito.mock(MercadoLibreRepository::class.java)
    private val useCase = GetProductDescriptionUseCase(repository)
    private val testProductId = "id123"

    @Test
    fun `getProductDescription success should return Resource_Success`() {

        /*** Run Coroutine ***/
        runBlocking {
            Mockito.`when`(repository.getProductDescription(testProductId)).thenReturn(
                /*** Simulate a SUCCESS Response ***/
                Resource.Success("General Description")
            )

            /*** Execute case ***/
            val result = useCase.invoke(testProductId)

            /*** Assert Response ***/
            TestCase.assertEquals(Resource.Success("General Description"), result
            )
        }//End CoroutineScope
    }//End Test

    @Test
    fun `getProductDescription no internet should return Resource_Error`() {

        val mockErrorMessage = "Connection unavailable"

        /*** Run Coroutine ***/
        runBlocking {
            Mockito.`when`(repository.getProductDescription(testProductId)).thenReturn(
                /*** Simulate IOException ERROR Response related to Connectivity ***/
                Resource.Error(IOException(mockErrorMessage))
            )

            /*** Execute case ***/
            val result = useCase.invoke(testProductId)
            val resultExpected: Resource<Throwable> = Resource.Error(Throwable(mockErrorMessage))

            /*** Assert that the Error is instance of Throwable Response ***/
            TestCase.assertEquals(resultExpected.javaClass, result.javaClass)
        }
    }
}