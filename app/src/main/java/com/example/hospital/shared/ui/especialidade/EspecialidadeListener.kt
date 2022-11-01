package com.example.hospital.shared.ui.especialidade

import com.example.hospital.shared.data.especialidade.Especialidade

interface EspecialidadeListener {
    fun openEdit(especialidade: Especialidade)
    fun delete(especialidade: Especialidade)
}