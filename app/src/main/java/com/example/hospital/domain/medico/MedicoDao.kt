package com.example.hospital.domain.medico

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedicoDao {
    @Query("SELECT * FROM medico")
    fun getAll(): List<Medico>

    @Query("SELECT * FROM medico WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Medico>

    @Query("SELECT * FROM medico WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Medico

    @Insert
    fun insert(vararg medicos: Medico)

    @Delete
    fun delete(medico: Medico)
}

