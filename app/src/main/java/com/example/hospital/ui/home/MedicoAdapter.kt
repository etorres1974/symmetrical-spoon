package com.example.hospital.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Medico
import com.example.hospital.databinding.ItemMedicoBinding


class MedicoAdapter(private var dataSet: List<Medico>, private var especialidades : List<Especialidade>) :
    RecyclerView.Adapter<MedicoAdapter.ViewHolder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = ItemMedicoBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val medico = dataSet[position]
        viewHolder.bind(medico, especialidades)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
    fun submit(list: List<Medico>?) {
        if(list != null) {
            this.dataSet = list
            notifyDataSetChanged()
        }
    }
    fun submitEspecialidade(list: List<Especialidade>?) {
        if(list != null) {
            this.especialidades = list
            notifyDataSetChanged()
        }
    }

    class ViewHolder(val view: ItemMedicoBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(medico: Medico, especialidades: List<Especialidade>){
            val especialidade = especialidades.firstOrNull() { it.id == medico.especId }
            with(view){
                this.tvNome.setText(medico.firstName)
                tvEspec.setText(especialidade?.descricao)
            }
        }
    }
}
