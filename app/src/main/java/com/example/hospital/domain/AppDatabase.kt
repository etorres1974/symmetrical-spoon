package com.example.hospital.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hospital.domain.especialidade.Especialidade
import com.example.hospital.domain.especialidade.EspecialidadeDao
import com.example.hospital.domain.medico.Medico
import com.example.hospital.domain.medico.MedicoDao
import com.example.hospital.domain.user.User
import com.example.hospital.domain.user.UserDao

@Database(entities = [User::class, Medico::class, Especialidade::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun medicoDao() : MedicoDao
    abstract fun especialidade() : EspecialidadeDao
}