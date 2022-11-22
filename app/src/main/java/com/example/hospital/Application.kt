package com.example.hospital

import android.app.Application
import androidx.room.Room
import com.example.hospital.shared.data.AppDatabase
import com.example.hospital.shared.data.dataModule
import com.example.hospital.shared.ui.uiModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@Application)
        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@Application)
            modules(getDBModule(), dataModule, uiModule )
        }
    }

    private fun getDBModule() = module {
        val db =  Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        single{
            db.especialidade()
        }
        single {
            db.medicoDao()
        }

        single {
            db.medicoFavoritoDao()
        }
    }
}