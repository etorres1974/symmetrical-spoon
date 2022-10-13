package com.example.hospital.data.especialidade

import androidx.room.*

@Dao
interface EspecialidadeDao {
    @Query("SELECT * FROM especialidade")
    fun getAll(): List<Especialidade>

    @Query("SELECT * FROM especialidade WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Especialidade>

    @Query("SELECT * FROM especialidade WHERE descricao LIKE :desc LIMIT 1")
    fun findByName(desc: String): Especialidade

    @Insert
    fun insert(vararg especialidades: Especialidade)

    @Delete
    fun delete(especialidade: Especialidade)

    @Update
    fun update(especialidade: Especialidade)
}

