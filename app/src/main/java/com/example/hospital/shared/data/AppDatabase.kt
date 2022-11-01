package com.example.hospital.shared.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.shared.data.especialidade.EspecialidadeDao
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.shared.data.medico.MedicoDao

@Database(entities = [Medico::class, Especialidade::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicoDao() : MedicoDao
    abstract fun especialidade() : EspecialidadeDao
}