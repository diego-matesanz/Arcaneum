package com.diego.matesanz.arcaneum.ui.screens.home.viewModel

import app.cash.turbine.test
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.test.unit.sampleBooks
import com.diego.matesanz.arcaneum.test.unit.testRules.CoroutinesTestRule
import com.diego.matesanz.arcaneum.usecases.FindBooksBySearchTextUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findBooksBySearchTextUseCase: FindBooksBySearchTextUseCase

    @Mock
    lateinit var getShelvesUseCase: GetShelvesUseCase

    @Mock
    lateinit var toggleBookShelfUseCase: ToggleBookShelfUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            findBooksBySearchTextUseCase = findBooksBySearchTextUseCase,
            getShelvesUseCase = getShelvesUseCase,
            toggleBookShelfUseCase = toggleBookShelfUseCase,
        )
    }

    @Test
    fun `Books are not requested if a search has not been performed`() = runTest {
        // Given
        viewModel.state.first()

        // When
        runCurrent()

        // Then
        verify(findBooksBySearchTextUseCase, never()).invoke(any<String>())
    }

    @Test
    fun `Books are requested if a search has been performed`() = runTest {
        // Given
        val books = sampleBooks("1", "2", "3", "4")
        whenever(findBooksBySearchTextUseCase.invoke(any<String>())).thenReturn(flowOf(books))

        // When
        viewModel.onAction(HomeAction.Search(any<String>()))

        // Then
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(books), awaitItem())
        }
    }
}
