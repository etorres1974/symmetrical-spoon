package com.example.hospital

import android.app.Application
import androidx.room.Room
import com.example.hospital.data.AppDatabase
import com.example.hospital.data.dataModule
import com.example.hospital.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

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
    }
}