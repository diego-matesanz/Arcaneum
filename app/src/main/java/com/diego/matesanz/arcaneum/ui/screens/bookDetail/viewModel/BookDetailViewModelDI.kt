package com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object BookDetailViewModelDI {

    @Provides
    @ViewModelScoped
    @Named("bookId")
    fun provideBookId(savedStateHandle: SavedStateHandle) =
        savedStateHandle.get<String>("bookId") ?: error("Book id not found")
}
