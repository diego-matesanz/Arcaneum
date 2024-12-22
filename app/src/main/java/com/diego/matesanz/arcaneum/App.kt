package com.diego.matesanz.arcaneum

import android.app.Application
import androidx.room.Room
import com.diego.matesanz.arcaneum.data.datasource.database.ArcaneumDatabase
import com.diego.matesanz.arcaneum.data.datasource.database.ArcaneumDatabase.Companion.MIGRATION_1_2
import com.diego.matesanz.arcaneum.data.datasource.database.DatabaseCallback
import com.diego.matesanz.arcaneum.data.datasource.database.DatabaseConstants.DATABASE_NAME

class App : Application() {

    lateinit var db: ArcaneumDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, ArcaneumDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            .addCallback(DatabaseCallback(this))
            .build()
    }
}
