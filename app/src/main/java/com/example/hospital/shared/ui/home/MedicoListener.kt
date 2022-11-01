package com.example.hospital.shared.ui.home

import com.example.hospital.shared.data.medico.Medico

interface MedicoListener {

    fun openEdit(medico: Medico)
    fun delete(medico: Medico)
}