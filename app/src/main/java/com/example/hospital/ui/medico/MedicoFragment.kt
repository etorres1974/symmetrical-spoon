package com.example.hospital.ui.medico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hospital.R
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedicoFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val medicoViewModel : MedicoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        medicoViewModel.medicoLivedata.observe(viewLifecycleOwner) {
            textView.text = it.toString()
        }
        medicoViewModel.especialidadeLivedata.observe(viewLifecycleOwner){
            setupSpinner(it)
        }
        return root
    }

    private fun setupSpinner(list : List<Especialidade>){
        val spinner = binding.spinnerEspecialidades
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
    }
}