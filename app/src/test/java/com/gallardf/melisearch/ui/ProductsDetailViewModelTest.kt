package com.gallardf.melisearch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gallardf.melisearch.data.Resource
import com.gallardf.melisearch.domain.usecases.GetProductDescriptionUseCase
import com.gallardf.melisearch.domain.usecases.GetProductDetailUseCase
import com.gallardf.melisearch.domain.models.ProductDetailModel
import com.gallardf.melisearch.ui.product_detail.ProductsDetailViewModel
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
import org.junit.jupiter.api.Assertions

@ExperimentalCoroutinesApi
class ProductsDetailViewModelTest{

    /*** Create abstract Function Rule  ***/
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    /*** Create object to Test ***/
    private lateinit var productsDetailViewModel: ProductsDetailViewModel

    /*** Define Mocks ***/
    @MockK
    lateinit var getProductDetailUseCase: GetProductDetailUseCase
    @MockK
    lateinit var getProductDescriptionUseCase: GetProductDescriptionUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        productsDetailViewModel =
            ProductsDetailViewModel(getProductDetailUseCase,getProductDescriptionUseCase)

        /*** Modifier Dispatcher to emulate Lifecycle ***/
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `getProductDetail success should update LiveData correctly`() {

        /*** Arrange Data ***/
        val testProductId = "id123"
        val fakeDescription = "Description Fake"
        val fakeProduct =  ProductDetailModel(
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

        /*** Simulate a SUCCESS Response ***/
        coEvery { getProductDetailUseCase.invoke(testProductId) } returns Resource.Success(fakeProduct)
        coEvery { getProductDescriptionUseCase.invoke(testProductId) } returns Resource.Success(fakeDescription)


        /*** Execute case ***/
        runBlocking {
            productsDetailViewModel.getDetail(testProductId)
            productsDetailViewModel.getDescription(testProductId)
        }
        val productDetailResult = productsDetailViewModel.productDetail.getOrAwaitValue()
        val productDescriptionResult = productsDetailViewModel.productDescription.getOrAwaitValue()

        /*** Assert Response ***/
        Assertions.assertNotNull(productDetailResult)
        Assertions.assertNotNull(productDescriptionResult)
        Assertions.assertEquals(fakeProduct, productDetailResult)
        Assertions.assertEquals(fakeDescription, productDescriptionResult)
    }

    @Test
    fun `getProductDetail error should update LiveData correctly`() {

        /*** Arrange Data ***/
        val mockProductId = "IdNoExistProduct"
        val errorMessage = "Product not found"

        /*** Simulate a Error Response ***/
        coEvery { getProductDetailUseCase.invoke(mockProductId) } returns Resource.Error(Exception(errorMessage))
        coEvery { getProductDescriptionUseCase.invoke(mockProductId) } returns Resource.Error(Exception(errorMessage))

        /*** Execute case ***/
        runBlocking {
            productsDetailViewModel.getDetail(mockProductId)
            productsDetailViewModel.getDescription(mockProductId)
        }
        val result = productsDetailViewModel.snackBarText.getOrAwaitValue()

        /*** Assert Response ***/
        Assertions.assertNotNull(result.getContentIfNotHandled())
    }
}