package com.example.hospital.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.especialidade.EspecialidadeDao
import com.example.hospital.data.medico.Medico
import com.example.hospital.data.medico.MedicoDao

@Database(entities = [Medico::class, Especialidade::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicoDao() : MedicoDao
    abstract fun especialidade() : EspecialidadeDao
}