package com.diego.matesanz.arcaneum

import android.app.Application
import androidx.room.Room
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_1_2
import com.diego.matesanz.arcaneum.framework.database.ArcaneumDatabase.Companion.MIGRATION_2_3
import com.diego.matesanz.arcaneum.framework.database.DatabaseCallback
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.DATABASE_NAME

class App : Application() {

    lateinit var db: ArcaneumDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, ArcaneumDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .addCallback(DatabaseCallback(this))
            .build()
    }
}
