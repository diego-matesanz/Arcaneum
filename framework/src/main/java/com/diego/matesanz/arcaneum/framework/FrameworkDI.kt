package com.diego.matesanz.arcaneum.framework

import android.app.Application
import androidx.room.Room
import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_1_2
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_2_3
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_3_4
import com.diego.matesanz.arcaneum.framework.database.DatabaseCallback
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.DATABASE_NAME
import com.diego.matesanz.arcaneum.framework.database.datasource.BooksRoomDataSource
import com.diego.matesanz.arcaneum.framework.database.datasource.ShelvesRoomDataSource
import com.diego.matesanz.arcaneum.framework.remote.BooksClient
import com.diego.matesanz.arcaneum.framework.remote.datasources.BooksServerDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object FrameworkDI {

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey() = BuildConfig.GOOGLE_BOOKS_API_KEY

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, ArcaneumDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
            .addCallback(DatabaseCallback(app))
            .build()

    @Provides
    fun providesBooksDao(db: ArcaneumDatabase) = db.booksDao()

    @Provides
    fun providesShelvesDao(db: ArcaneumDatabase) = db.shelvesDao()

    @Provides
    @Singleton
    fun provideBooksClient(@Named("apiKey") apiKey: String) = BooksClient(apiKey).instance
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FrameworkBindsDI {

    @Binds
    abstract fun bindBooksRemoteDataSource(booksServerDataSource: BooksServerDataSource): BooksRemoteDataSource

    @Binds
    abstract fun bindBooksLocalDataSource(booksRoomDataSource: BooksRoomDataSource): BooksLocalDataSource

    @Binds
    abstract fun bindShelvesLocalDataSource(shelvesRoomDataSource: ShelvesRoomDataSource): ShelvesLocalDataSource
}
