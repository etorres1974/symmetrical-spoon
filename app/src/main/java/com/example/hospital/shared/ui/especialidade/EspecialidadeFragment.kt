package com.example.hospital.shared.ui.especialidade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital.shared.data.especialidade.Especialidade
import com.example.hospital.databinding.FragmentEspecialidadeBinding
import com.example.hospital.shared.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EspecialidadeFragment : Fragment(), EspecialidadeListener {

    private val medicoViewModel: MedicoViewModel by sharedViewModel()

    private lateinit var binding: FragmentEspecialidadeBinding

    private val adapter = EspecialidadeAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEspecialidadeBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            adicionarEspecialidade()
        }
        binding.rvEspecialidades.adapter = adapter
        binding.rvEspecialidades.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        medicoViewModel.especialidadeLivedata.observe(viewLifecycleOwner) {
            adapter.submit(it)
        }
        return binding.root
    }

    override fun openEdit(especialidade: Especialidade) {
        binding.etNome.setText(especialidade.descricao)

        if (edit != especialidade) {
            edit = especialidade
            binding.fab.setImageResource(com.example.hospital.R.drawable.ic_baseline_edit_24)
            binding.textNotifications.text = "Editar Especialidade"
            binding.fabCancel.visibility = View.VISIBLE
            binding.fabCancel.setOnClickListener {
                openEdit(especialidade)
            }
        } else {
            edit = null
            binding.etNome.setText("")
            binding.fab.setImageResource(com.example.hospital.R.drawable.ic_baseline_add_24)
            binding.textNotifications.text = "Adicionar Especialidade"
            binding.fabCancel.visibility = View.GONE
        }

    }

    var edit: Especialidade? = null
    private fun adicionarEspecialidade() {
        edit?.let {
            medicoViewModel.editarEspecialidade(it, binding.etNome.text.toString())
        } ?: medicoViewModel.adicionarEspecialidade(binding.etNome.text.toString())
        binding.etNome.setText("")
        showToast("Especialidade Adicionada com Sucesso")
    }

    override fun delete(especialidade: Especialidade) {
        medicoViewModel.removerEspecialiadde(especialidade.id) {
            binding.root.post {
                showToast("Especialidade nao pode ser excluida por estar vinculada a medicos cadastrados ")
            }
        }
    }

    fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

}