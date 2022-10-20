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
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.databinding.FragmentNotificationsBinding
import com.example.hospital.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EspecialidadeFragment : Fragment() {

    private val medicoViewModel : MedicoViewModel by viewModel()

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(EspecialidadeViewModel::class.java)

        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return binding.root
    }

}