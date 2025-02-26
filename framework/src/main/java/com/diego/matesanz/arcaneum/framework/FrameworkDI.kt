package com.diego.matesanz.arcaneum.framework

import androidx.room.Room
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_1_2
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_2_3
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_3_4
import com.diego.matesanz.arcaneum.framework.database.DatabaseCallback
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.DATABASE_NAME
import com.diego.matesanz.arcaneum.framework.remote.BooksClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

@Module
@ComponentScan
class FrameworkDI

val frameworkKoinModule = module {
    single {
        Room.databaseBuilder(get(), ArcaneumDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
            .addCallback(DatabaseCallback(get()))
            .build()
    }
    factory { get<ArcaneumDatabase>().booksDao() }
    factory { get<ArcaneumDatabase>().shelvesDao() }
    single { BooksClient.instance }
}
