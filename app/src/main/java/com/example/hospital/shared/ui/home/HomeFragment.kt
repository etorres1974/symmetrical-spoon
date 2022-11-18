package com.example.hospital.shared.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital.R
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.databinding.FragmentHomeBinding
import com.example.hospital.shared.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), MedicoListener {

    private  lateinit var binding: FragmentHomeBinding

    private val medicoViewModel : MedicoViewModel by sharedViewModel()
    private val adapter =  MedicoAdapter(this, true)
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
            medicosFiltradosLiveData.observe(viewLifecycleOwner) {
                binding.rvMedicos.layoutManager = LinearLayoutManager(requireContext() ,LinearLayoutManager.VERTICAL, false)
                adapter.submit(it)
                Log.d("Teste123", it.firstOrNull().toString() )
            }
            binding.etSearch.addTextChangedListener{

                this.filterMedicos(it?.toString() ?: "")
            }
            especialidadeLivedata.observe(viewLifecycleOwner){
                setupSpinner(it)
            }
        }
        return binding.root
    }

    private fun setupSpinner(list: List<Especialidade>) {
        val spinner = binding.spinnerEspecialidades
        val itens = list.toMutableList().apply {
            add(0, Especialidade(0, "Filtrar Medico por especialidade"))
        }
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itens)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("Teste123 index", p2.toString())
                medicoViewModel.selecionarEspecialidadeFiltro(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                medicoViewModel.selecionarEspecialidadeFiltro(0)
            }

        }
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

    override fun favorite(medico: Medico) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Teste123", " HOME FRAG DESTROYED")
    }
}