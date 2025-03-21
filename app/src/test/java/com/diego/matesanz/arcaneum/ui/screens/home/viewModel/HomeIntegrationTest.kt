package com.diego.matesanz.arcaneum.ui.screens.home.viewModel

import app.cash.turbine.test
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.test.unit.data.buildBooksRepositoryWith
import com.diego.matesanz.arcaneum.test.unit.data.buildShelvesRepositoryWith
import com.diego.matesanz.arcaneum.test.unit.domain.book.sampleBooks
import com.diego.matesanz.arcaneum.test.unit.domain.shelf.sampleShelves
import com.diego.matesanz.arcaneum.test.unit.testRules.CoroutinesTestRule
import com.diego.matesanz.arcaneum.usecases.FindBooksBySearchTextUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Data is loaded from server when a search is performed`() = runTest {
        // Given
        val remoteBooks = sampleBooks("1", "2", "3", "4")
        val localShelves = sampleShelves(1, 2, 3, 4)
        val viewModel = buildViewModelWith(remoteBooks = remoteBooks, localShelves = localShelves)

        // When
        viewModel.onAction(HomeAction.Search("Search"))

        // Then
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(Pair(remoteBooks, localShelves)), awaitItem())
        }
    }

    @Test
    fun `Book is saved when bookmarked in a shelf`() = runTest {
        // Given
        val remoteBooks = sampleBooks("1", "2", "3", "4")
        val localShelves = sampleShelves(1, 2, 3, 4)
        val viewModel = buildViewModelWith(
            remoteBooks = remoteBooks,
            localShelves = localShelves
        )

        // When
        viewModel.onAction(HomeAction.Bookmarked(shelfId = 2, book = remoteBooks[0]))

        // Then
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(
                remoteBooks[0].copy(shelfId = 2),
                (awaitItem() as Result.Success).data.first[0]
            )
        }
    }

    private fun buildViewModelWith(
        localBooks: List<Book> = emptyList(),
        remoteBooks: List<Book> = emptyList(),
        localShelves: List<Shelf> = emptyList(),
    ): HomeViewModel {
        val booksRepository = buildBooksRepositoryWith(
            localBooks = localBooks,
            remoteBooks = remoteBooks,
        )
        val shelvesRepository = buildShelvesRepositoryWith(localShelves = localShelves)

        return HomeViewModel(
            findBooksBySearchTextUseCase = FindBooksBySearchTextUseCase(booksRepository),
            getShelvesUseCase = GetShelvesUseCase(shelvesRepository),
            toggleBookShelfUseCase = ToggleBookShelfUseCase(booksRepository),
        )
    }
}
