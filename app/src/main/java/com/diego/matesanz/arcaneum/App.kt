package com.diego.matesanz.arcaneum

import android.app.Application
import com.diego.matesanz.arcaneum.data.DataDI
import com.diego.matesanz.arcaneum.framework.FrameworkDI
import com.diego.matesanz.arcaneum.framework.frameworkKoinModule
import com.diego.matesanz.arcaneum.usecases.UseCasesDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                AppDI().module,
                DataDI().module,
                UseCasesDI().module,
                FrameworkDI().module,
                frameworkKoinModule,
            )
        }
    }
}
