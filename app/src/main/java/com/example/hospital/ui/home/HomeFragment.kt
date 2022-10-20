package com.example.hospital.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Medico
import com.example.hospital.databinding.FragmentHomeBinding
import com.example.hospital.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), MedicoListener {

    private  lateinit var binding: FragmentHomeBinding

    private val medicoViewModel : MedicoViewModel by sharedViewModel()
    private val adapter =  MedicoAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvMedicos.adapter = adapter
        with(medicoViewModel){
            especialidadeLivedata.observe(viewLifecycleOwner){
                adapter.submitEspecialidade(it)
            }
            medicoLivedata.observe(viewLifecycleOwner) {
                binding.rvMedicos.layoutManager = LinearLayoutManager(requireContext() ,LinearLayoutManager.VERTICAL, false)
                adapter.submit(it)
            }

        }
        return binding.root
    }

    override fun openEdit(medico: Medico) {
        val bundle = Bundle().apply {
            putSerializable("medico", medico)
        }
        findNavController().navigate(R.id.action_navigation_home_to_navigation_medico, bundle)

    }

    override fun delete(medico: Medico) {
        medicoViewModel.removerMedico(medico.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Teste123", " HOME FRAG DESTROYED")
    }
}