package com.example.hospital.domain

import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.especialidade.EspecialidadeDao
import com.example.hospital.data.medico.Address
import com.example.hospital.data.medico.Medico
import com.example.hospital.data.medico.MedicoDao

class EspecialidadeRepository(val dao : EspecialidadeDao ) {

    fun getAll() = dao.getAll()

    fun adicionar(nome : String) : List<Especialidade>{
        val model = Especialidade(0, nome)
        dao.insert(model)
        return dao.getAll()
    }


    fun editar(id : Int, nome : String): List<Especialidade>{
        val model = Especialidade(id = id, descricao = nome)
        dao.update(model)
        return dao.getAll()
    }

    fun remover(id: Int){
        //Verificar se nao tem medicos associados antes
        dao.deleteById(id)
    }

    fun filtrar(query : String, especialidades : List<Especialidade>): List<Especialidade> {
        return especialidades.filter { it.descricao.contains(query) }
    }
}