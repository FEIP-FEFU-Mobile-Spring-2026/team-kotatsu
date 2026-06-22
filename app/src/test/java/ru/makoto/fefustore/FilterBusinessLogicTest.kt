package ru.makoto.fefustore

import android.content.Context
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.makoto.fefustore.data.repositories.StoreRepository
import ru.makoto.fefustore.viewmodels.ProductsViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class FilterBusinessLogicTest {
    private val repository: StoreRepository = mockk(relaxed = true)
    private val context: Context = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: ProductsViewModel

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
    fun `setCategory with null clears categories in repository`() =
        runTest {
            viewModel.setCategory(null)
            coVerify(exactly = 1) { repository.clearCategories() }
            coVerify(exactly = 0) { repository.selectCategory(any()) }
        }

    @Test
    fun `setCategory with ID selects category in repository`() =
        runTest {
            val categoryId = "cat_jeans"
            viewModel.setCategory(categoryId)
            coVerify(exactly = 1) { repository.selectCategory(categoryId = categoryId) }
            coVerify(exactly = 0) { repository.clearCategories() }
        }
}
