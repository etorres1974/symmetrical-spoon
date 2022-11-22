package com.example.hospital.shared.domain

import com.example.hospital.shared.data.medico.MedicoFavorito
import com.example.hospital.shared.data.medico.MedicoFavoritoDao

class MedicoFavoritoRepository(val dao : MedicoFavoritoDao) {

    suspend fun getAll() = dao.getAll()

    suspend fun adicionar(medicoId : Int, usuarioId: String) : List<MedicoFavorito> {
        val model = MedicoFavorito(0, medicoId, usuarioId)
        dao.insert(model)
        return dao.getAll()
    }

    suspend fun deletar(medicoId : Int, usuarioId: String) : List<MedicoFavorito>{
        dao.delete(usuarioId, medicoId)
        return dao.getAll()
    }

}