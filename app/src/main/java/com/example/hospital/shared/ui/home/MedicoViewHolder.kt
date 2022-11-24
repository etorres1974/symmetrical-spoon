package com.example.hospital.shared.ui.home

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.databinding.ItemMedicoBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MedicoViewHolder(val view: ItemMedicoBinding, val listener: MedicoListener,val admin: Boolean) : RecyclerView.ViewHolder(view.root) {

    fun bind(medico: Medico, especialidades: List<Especialidade>, isFavorito:Boolean){
        val especialidade = especialidades.firstOrNull() { it.id == medico.especId }
        with(view){
            tvNome.text = medico.firstName
            tvSobrenome.text = medico.lastName
            tvEspec.text = especialidade?.descricao
            tvFone.text = medico.telefone
            tvAddress.text = medico.address?.toString() ?: ""
            btnDelete.setOnClickListener { listener.delete(medico) }
            btnEdit.setOnClickListener { listener.openEdit(medico) }
            btnFavorito.setOnClickListener{listener.favorite(medico, isFavorito)}
            if(!isFavorito){
                btnFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            else{
                btnFavorito.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            btnFavorito.isVisible = !admin && Firebase.auth.currentUser != null
            btnDelete.isVisible = admin
            btnEdit.isVisible = admin
        }
    }
}