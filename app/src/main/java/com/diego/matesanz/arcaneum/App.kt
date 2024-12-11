package com.diego.matesanz.arcaneum

import android.app.Application
import androidx.room.Room
import com.diego.matesanz.arcaneum.data.datasource.database.ArcaneumDatabase

class App : Application() {

    lateinit var db: ArcaneumDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, ArcaneumDatabase::class.java, "arcaneum-db").build()
    }
}
