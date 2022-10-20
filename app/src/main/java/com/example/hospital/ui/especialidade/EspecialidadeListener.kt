package com.example.hospital.ui.especialidade

import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Medico

interface EspecialidadeListener {
    fun openEdit(especialidade: Especialidade)
    fun delete(especialidade: Especialidade)
}