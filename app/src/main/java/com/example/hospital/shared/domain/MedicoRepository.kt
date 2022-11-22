package com.example.hospital.shared.domain

import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.shared.data.medico.Address
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.shared.data.medico.MedicoDao

class MedicoRepository(val dao : MedicoDao ) {

    suspend fun getAll() = dao.getAll()

    suspend fun adicionar(especialidade: Especialidade, fullName : String, telefone: String?, address: Address?) : List<Medico>{
        val model = Medico.instance(especialidade, fullName, telefone, address)
        dao.insert(model)
        return dao.getAll()
    }


    suspend fun editar(id : Int, especialidade: Especialidade, fullName : String, telefone: String?, address: Address?): List<Medico>{
        val model = Medico.instance(especialidade, fullName, telefone, address, id = id)
        dao.update(model)
        return dao.getAll()
    }

    fun remover(id: Int){
        dao.deleteById(id)
    }

    fun filtrar(especialidade: Especialidade, medicos : List<Medico>): List<Medico> {
        return medicos.filter { it.especId == especialidade.id }
    }

    fun filtrar(query : String, medicos : List<Medico>): List<Medico> {
        return medicos.filter { it.filterByName(query) || it.filterByPhone(query)}
    }


    private fun Medico.filterByName(query : String) : Boolean = firstName?.contains(query) ?: false || lastName?.contains(query) ?: false
    private fun Medico.filterByPhone(query: String) : Boolean = telefone?.contains(query) ?: false

}