package com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object ShelfDetailViewModelDI {

    @Provides
    @ViewModelScoped
    @Named("shelfId")
    fun provideShelfId(savedStateHandle: SavedStateHandle) =
        savedStateHandle.get<Int>("shelfId") ?: throw IllegalStateException("Missing shelfId")
}
