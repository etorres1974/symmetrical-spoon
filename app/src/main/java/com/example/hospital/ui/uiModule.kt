package com.example.hospital.ui

import com.example.hospital.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel {
        MedicoViewModel(
            especialidadeRepository = get(),
            medicoRepository = get()
        )
    }
}