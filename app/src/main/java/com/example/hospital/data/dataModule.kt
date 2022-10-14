package com.example.hospital.data
import com.example.hospital.domain.EspecialidadeRepository
import com.example.hospital.domain.MedicoRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        EspecialidadeRepository(dao = get())
    }
    single {
        MedicoRepository(dao = get())
    }
}