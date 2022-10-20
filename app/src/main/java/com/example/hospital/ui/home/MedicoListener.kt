package com.example.hospital.ui.home

import com.example.hospital.data.medico.Medico

interface MedicoListener {

    fun openEdit(medico: Medico)
    fun delete(medico: Medico)
}