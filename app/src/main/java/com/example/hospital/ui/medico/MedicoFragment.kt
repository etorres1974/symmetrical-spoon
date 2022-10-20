package com.example.hospital.ui.medico

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Address
import com.example.hospital.databinding.FragmentMedicoBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MedicoFragment : Fragment() {

    private lateinit var binding: FragmentMedicoBinding
    private val medicoViewModel: MedicoViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedicoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medicoViewModel.especialidadeLivedata.observe(viewLifecycleOwner) {
            Log.d("Teste123", "Nova lista ->$it")
            setupSpinner(it)
        }
        binding.fab.setOnClickListener {
            adicionarMedico()
        }
    }

    private fun setupSpinner(list: List<Especialidade>) {
        val spinner = binding.spinnerEspecialidades
        val itens = list.toMutableList().apply {
            add(0, Especialidade(0, "Especialidade"))
        }
        spinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itens)

    }

    private fun adicionarMedico() = with(binding) {
        if(binding.spinnerEspecialidades.selectedItemPosition == 0) {
            binding.spinnerEspecialidades.requestFocus()
            showToast("Especialidade é um campo obrigatorio")
        }else {
            (spinnerEspecialidades.selectedItem as? Especialidade)?.let { especialidade ->
                medicoViewModel.adicionarMedico(
                    especialidade = especialidade,
                    fullName = etNome.text.toString() + " " + etSobrenome.text.toString(),
                    telefone = etTelefone.text.toString(),
                    address = Address(
                        street = etRua.text.toString(),
                        state = etEstado.text.toString(),
                        city = etCidade.text.toString()
                    )
                )
                createSuccess()
            }
        }
    }

    private fun createSuccess(){
        with(binding){
            etNome.setText("")
            etSobrenome.setText("")
            spinnerEspecialidades.setSelection(0)
            etTelefone.setText("")
            etCidade.setText("")
            etRua.setText("")
            etEstado.setText("")
        }
    }

    fun showToast(text : String){
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}