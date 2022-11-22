package com.example.hospital.shared.data
import com.example.hospital.shared.domain.EspecialidadeRepository
import com.example.hospital.shared.domain.MedicoFavoritoRepository
import com.example.hospital.shared.domain.MedicoRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        EspecialidadeRepository(dao = get())
    }
    single {
        MedicoRepository(dao = get())
    }

    single {
        MedicoFavoritoRepository(dao = get())
    }
}