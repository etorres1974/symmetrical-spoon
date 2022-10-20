package com.example.hospital.ui.especialidade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hospital.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EspecialidadeViewModel : ViewModel() {



    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}