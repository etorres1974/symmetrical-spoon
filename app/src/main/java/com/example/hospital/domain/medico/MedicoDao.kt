package com.example.hospital.domain.medico

import androidx.room.*

@Dao
interface MedicoDao {
    @Query("SELECT * FROM medico")
    fun getAll(): List<Medico>

    @Transaction
    @Query("SELECT * FROM Medico")
    fun getAllMedicosEspecialidade(): List<MedicoEspecialidade>

    @Query("SELECT * FROM medico WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Medico>

    @Query("SELECT * FROM medico WHERE first_name LIKE :first or " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Medico

    @Query("SELECT * FROM medico WHERE first_name LIKE :first or " +
            "last_name LIKE :last LIMIT 1")
    fun findMedEspec(first: String, last: String): MedicoEspecialidade

    @Insert
    fun insert(vararg medicos: Medico)

    @Delete
    fun delete(medico: Medico)


}

