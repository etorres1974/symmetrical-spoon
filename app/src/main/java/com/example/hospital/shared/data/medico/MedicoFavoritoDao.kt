package com.example.hospital.shared.data.medico

import androidx.room.*

@Dao
interface MedicoFavoritoDao {
    @Query("SELECT * FROM medicoFavorito")
    suspend fun getAll(): List<MedicoFavorito>

    @Insert
    suspend fun insert(vararg medicoFavoritos: MedicoFavorito)

    @Insert
    fun insert(medicoFavorito: MedicoFavorito)

    @Query("delete from medicofavorito where usuarioId = :usuarioId and medicoId = :medicoId")
    fun delete(usuarioId:String, medicoId: Int)


}

