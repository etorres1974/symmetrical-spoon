package com.example.hospital.shared.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.databinding.ItemMedicoBinding


class MedicoAdapter( val listener: MedicoListener, val admin:Boolean) :
    RecyclerView.Adapter<MedicoViewHolder>() {

    private var dataSet: List<Medico> = emptyList()
    private var especialidades : List<Especialidade> = emptyList()

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MedicoViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = ItemMedicoBinding.inflate(inflater, viewGroup, false)
        return MedicoViewHolder(view, listener, admin)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MedicoViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val medico = dataSet[position]
        viewHolder.bind(medico, especialidades)
    }


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
}
