package com.gallardf.melisearch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.usecases.GetProductsByQueryUseCase
import com.gallardf.melisearch.domain.models.ProductModel
import com.gallardf.melisearch.ui.main.ProductsViewModel
import com.gallardf.melisearch.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    /*** Create abstract Function Rule  ***/
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    /*** Create object to Test ***/
    private lateinit var productViewModel: ProductsViewModel

    /*** Define Mocks ***/
    @MockK
    lateinit var getProductsByQueryUseCase: GetProductsByQueryUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        productViewModel = ProductsViewModel(getProductsByQueryUseCase)

        /*** Modifier Dispatcher to emulate Lifecycle ***/
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `searchProductsByQuery success should update LiveData correctly`() {

        /*** Arrange Data ***/
        val testQuery = "Samsung"
        val fakeProducts = listOf(
            ProductModel(
                id = "666",
                price = 5559.66f,
                title = "Samsung Test",
                imageUrl = "something",
                acceptsMercadoPago = true
            )
        )

        /*** Simulate a SUCCESS Response ***/
        coEvery { getProductsByQueryUseCase.getProductsByName(testQuery) } returns Resource.Success(fakeProducts)

        /*** Execute case ***/
        runBlocking {
            productViewModel.searchProductsByQuery(testQuery)
        }
        val result = productViewModel.products.getOrAwaitValue()

        /*** Assert Response ***/
        assertNotNull(result)
        assertEquals(fakeProducts,result)
    }

    @Test
    fun `searchProductsByQuery error should update LiveData correctly`() {

        /*** Arrange Data ***/
        val testQuery = "NoExistProduct"
        val errorMessage = "Product not found"

        /*** Simulate a Error Response ***/
        coEvery { getProductsByQueryUseCase.getProductsByName(testQuery) } returns Resource.Error(Exception(errorMessage))

        /*** Execute ***/
        runBlocking {
            productViewModel.searchProductsByQuery(testQuery)
        }
        val result = productViewModel.errorAlert.getOrAwaitValue()

        /*** Assert Response ***/
        assertNotNull(result.getContentIfNotHandled())
    }
}