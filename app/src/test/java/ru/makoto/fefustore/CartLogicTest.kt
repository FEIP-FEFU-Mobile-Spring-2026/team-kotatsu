package ru.makoto.fefustore

import android.content.Context
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.makoto.fefustore.Data.DTO.CartItem
import ru.makoto.fefustore.Data.DTO.Clothes
import ru.makoto.fefustore.Data.Repositories.StoreRepository
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class CartBusinessLogicTest {

    private val repository: StoreRepository = mockk(relaxed = true)
    private val context: Context = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ProductsViewModel

    private fun createTestClothes(id: String, price: Int) = Clothes(
        id = id, title = "Clothes", description = "", longDescription = "", price = price,
        img = "", category = "", material = "", weight = "", season = "", countryOfOrigin = "",
        sizes = emptyList(), tags = emptyList()
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { repository.hasCachedData() } returns MutableStateFlow(true)
        every { repository.getAllClothes() } returns MutableStateFlow(emptyList())
        every { repository.getAllCategories() } returns MutableStateFlow(emptyList())
        every { repository.getAllClothesInCart() } returns MutableStateFlow(emptyList())
        every { repository.getSelectedCategory() } returns MutableStateFlow(null)

        viewModel = ProductsViewModel(repository, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `cartTotalPrice calculates correct sum based on items and amounts`() = runTest {
        val mockClothes1 = createTestClothes(id = "1", price = 10000)
        val mockClothes2 = createTestClothes(id = "2", price = 20000)

        val cartItems = listOf(
            CartItem(id = 1, clothes = mockClothes1, amount = 2, selectedSize = null),
            CartItem(id = 2, clothes = mockClothes2, amount = 1, selectedSize = null)
        )

        every { repository.getAllClothesInCart() } returns MutableStateFlow(cartItems)
        val vm = ProductsViewModel(repository, context)

        backgroundScope.launch(testDispatcher) {
            vm.cartTotalPrice.collect {}
        }

        assertEquals(40000, vm.cartTotalPrice.value)
    }

    @Test
    fun `checkoutOrder clears cart in repository`() = runTest {
        viewModel.checkoutOrder()
        coVerify(exactly = 1) { repository.clearCart() }
    }
}