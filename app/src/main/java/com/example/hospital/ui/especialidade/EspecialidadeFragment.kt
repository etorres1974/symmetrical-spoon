package com.example.hospital.ui.especialidade

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hospital.databinding.FragmentEspecialidadeBinding
import com.example.hospital.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EspecialidadeFragment : Fragment() {

    private val medicoViewModel : MedicoViewModel by sharedViewModel()

    private lateinit var binding: FragmentEspecialidadeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(EspecialidadeViewModel::class.java)

        binding = FragmentEspecialidadeBinding.inflate(inflater, container, false)

        notificationsViewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
        binding.fab.setOnClickListener {
            medicoViewModel.adicionarEspecialidade(binding.etNome.text.toString())
        }

        return binding.root
    }

}