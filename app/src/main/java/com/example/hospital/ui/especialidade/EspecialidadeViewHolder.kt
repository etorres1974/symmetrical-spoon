package com.example.hospital.ui.especialidade

import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Medico
import com.example.hospital.databinding.ItemEspecialidadeBinding
import com.example.hospital.databinding.ItemMedicoBinding

class EspecialidadeViewHolder(val view: ItemEspecialidadeBinding, val listener : EspecialidadeListener) : RecyclerView.ViewHolder(view.root) {

    fun bind(especialidade: Especialidade){
        with(view){
            tvEspec.text = especialidade?.descricao
            btnDelete.setOnClickListener { listener.delete(especialidade) }
            btnEdit.setOnClickListener { listener.openEdit(especialidade) }
        }
    }
}