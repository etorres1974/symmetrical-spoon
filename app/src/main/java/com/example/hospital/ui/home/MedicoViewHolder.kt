package com.example.hospital.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Medico
import com.example.hospital.databinding.ItemMedicoBinding

class MedicoViewHolder(val view: ItemMedicoBinding,val listener : MedicoListener) : RecyclerView.ViewHolder(view.root) {

    fun bind(medico: Medico, especialidades: List<Especialidade>){
        val especialidade = especialidades.firstOrNull() { it.id == medico.especId }
        with(view){
            tvNome.text = medico.firstName
            tvSobrenome.text = medico.lastName
            tvEspec.text = especialidade?.descricao
            tvFone.text = medico.telefone
            tvAddress.text = medico.address?.toString() ?: ""
            btnDelete.setOnClickListener { listener.delete(medico) }
            btnEdit.setOnClickListener { listener.openEdit(medico) }
        }
    }
}