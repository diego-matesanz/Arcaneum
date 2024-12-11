package com.diego.matesanz.arcaneum

import android.app.Application
import androidx.room.Room
import com.diego.matesanz.arcaneum.data.datasource.database.ArcaneumDatabase
import com.diego.matesanz.arcaneum.data.datasource.database.ArcaneumDatabase.Companion.DATABASE_NAME
import com.diego.matesanz.arcaneum.data.datasource.database.DatabaseCallback

class App : Application() {

    lateinit var db: ArcaneumDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, ArcaneumDatabase::class.java, DATABASE_NAME)
            .addCallback(DatabaseCallback(this))
            .build()
    }
}
