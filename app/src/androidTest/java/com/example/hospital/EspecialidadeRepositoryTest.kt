package com.example.hospital

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hospital.data.AppDatabase
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.especialidade.EspecialidadeDao
import com.example.hospital.data.medico.Medico
import com.example.hospital.domain.EspecialidadeRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EspecialidadeRepositoryTest {
    private lateinit var especDao : EspecialidadeDao
    private lateinit var db: AppDatabase
    private lateinit var repo : EspecialidadeRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        especDao = db.especialidade()
        repo = EspecialidadeRepository(especDao)
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
        assert(!repo.getAll().any { it.descricao == "Urologista" })
        repo.adicionar("Urologista")
        val result = repo.getAll().last()
        assert(result.descricao == "Urologista")
        repo.remover(result.id)
        assert(!repo.getAll().any { it.descricao == "Urologista" })
    }

    @Test(expected = SQLiteConstraintException::class)
    fun remover_especialidade_em_uso_lanca_excecao(){
        repo.remover(1)
    }

    @Test
    fun editar_medico(){
        val espec  = repo.getAll().first()
        assert(espec.descricao == "Ortopedista")
        repo.editar(1, "Urologista")
        assert(repo.getAll().first().descricao == "Urologista")
    }
    @Test
    fun filtraPorNome(){
        val medicos = repo.getAll()
        assert(medicos.size == 5)
        val result = repo.filtrar("Ortopedista", medicos)
        assert(result.first().descricao == "Ortopedista" )
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