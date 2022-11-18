package com.example.hospital.user.ui.home

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital.databinding.FragmentHomeBinding
import com.example.hospital.databinding.FragmentHomeUserBinding
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.shared.ui.home.MedicoAdapter
import com.example.hospital.shared.ui.home.MedicoListener
import com.example.hospital.shared.ui.medico.MedicoViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), MedicoListener {

    private lateinit var binding: FragmentHomeUserBinding

    // This property is only valid between onCreateView and
    // onDestroyView.




    private val medicoViewModel : MedicoViewModel by sharedViewModel()
    private val adapter =  MedicoAdapter(this, false)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        binding.rvMedicos.adapter = adapter
        with(medicoViewModel){
            especialidadeLivedata.observe(viewLifecycleOwner){
                adapter.submitEspecialidade(it)
            }
            medicosFiltradosLiveData.observe(viewLifecycleOwner) {
                binding.rvMedicos.layoutManager = LinearLayoutManager(requireContext() ,
                    LinearLayoutManager.VERTICAL, false)
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
        spinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, itens)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWelcome()
        medicoViewModel.setupInitialData()
    }

    private fun setupWelcome(){
        val user = Firebase.auth.currentUser
        if(user == null){
            binding.tvWelcome.text = "Bem vindo Convidado"
        }else {
            binding.tvWelcome.text = "Bem vindo ${user?.displayName}"
        }
    }


    override fun openEdit(medico: Medico) {
        TODO("Not yet implemented")
    }

    override fun delete(medico: Medico) {
        TODO("Not yet implemented")
    }

    override fun favorite(medico: Medico) {
        TODO("Not yet implemented")
    }
}