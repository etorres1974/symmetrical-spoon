package com.example.hospital.shared.ui.especialidade

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.databinding.ItemEspecialidadeBinding


class EspecialidadeAdapter(val listener: EspecialidadeListener) :
    RecyclerView.Adapter<EspecialidadeViewHolder>() {

    private var dataSet: List<Especialidade> = emptyList()

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EspecialidadeViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = ItemEspecialidadeBinding.inflate(inflater, viewGroup, false)
        return EspecialidadeViewHolder(view, listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: EspecialidadeViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val especialidade= dataSet[position]
        viewHolder.bind(especialidade)
    }


    fun submit(list: List<Especialidade>?) {
        if(list != null) {
            this.dataSet = list
            notifyDataSetChanged()
        }
    }
}
