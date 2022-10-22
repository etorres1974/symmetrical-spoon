package com.example.hospital

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hospital.data.AppDatabase
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.especialidade.EspecialidadeDao
import com.example.hospital.data.medico.Medico
import com.example.hospital.domain.MedicoRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MedicoRepositoryTest {
    private lateinit var especDao : EspecialidadeDao
    private lateinit var db: AppDatabase
    private lateinit var repo : MedicoRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        especDao = db.especialidade()
        repo = MedicoRepository(db.medicoDao())
        especDao.insert(*mockEspecialidades.toTypedArray())
        db.medicoDao().insert(*mockListaMedicos(especDao.getAll()).toTypedArray())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insere_e_remove(){
        assert(!repo.getAll().any { it.firstName == "Otavio" })
        val especialidade = especDao.getAll().first()
        repo.adicionar(especialidade, "Otavio", null, null)
        val result = repo.getAll().last()
        assert(result.firstName == "Otavio")
        repo.remover(result.id)
        assert(!repo.getAll().any { it.firstName == "Otavio" })
    }

    @Test
    fun editar_medico(){
        val medico = repo.getAll().first()
        assert(medico.firstName == "Orfeu")
        repo.editar(medico.id, Especialidade(1, ""), "Morfeu", null, null)
        assert(repo.getAll().first().firstName == "Morfeu")
    }
    @Test
    fun filtraPorNome(){
        val medicos = repo.getAll()
        assert(medicos.size == 5)
        val result = repo.filtrar("Orfeu", medicos)
        assert(result.first().firstName == "Orfeu" )
    }

    @Test
    fun filtraPorEspecialidade(){
        val medicos = repo.getAll()
        val espec = especDao.getAll().first()
        assert(medicos.size == 5)
        val result = repo.filtrar(espec, medicos)
        assert(result.first().especId == espec.id )
    }

    private val mockEspecialidades = listOf(
        Especialidade(0, "Ortopedista"),
        Especialidade(0, "Cardiologista"),
        Especialidade(0, "Pediatra"),
        Especialidade(0, "Neurologista"),
        Especialidade(0, "Geriatra")
    )
    private fun  mockListaMedicos(mockEspecialidades : List<Especialidade>) = listOf(
        Medico.instance(mockEspecialidades[0], "Orfeu"),
        Medico.instance(mockEspecialidades[1], "Carlos"),
        Medico.instance(mockEspecialidades[2], "Pedro"),
        Medico.instance(mockEspecialidades[3], "Newton"),
        Medico.instance(mockEspecialidades[4], "Geraldo")
    )

}