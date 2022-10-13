package com.example.hospital

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hospital.domain.AppDatabase
import com.example.hospital.domain.especialidade.Especialidade
import com.example.hospital.domain.medico.Medico
import com.example.hospital.domain.user.User
import com.example.hospital.domain.user.UserDao
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user = User(uid = 3, "george", "lucas")
        userDao.insert(user)
        val byName = userDao.findByName("george", "lucas")
        assertThat(byName, equalTo(user))
    }

    @Test
    @Throws(Exception::class)
    fun editar_nome_especialidade() {
        val especialidade = Especialidade(1, "Ortopedista")
        val dao = db.especialidade()

        assert(dao.getAll().isEmpty())
        dao.insert(especialidade)
        assert(dao.getAll().first() == especialidade)

        especialidade.descricao = "Dentista"
        dao.update(especialidade)
        val resultado =  dao.getAll().first().descricao
        assert(resultado == "Dentista"){ "Deverria ser dentista porem veio $resultado" }
    }

    @Test
    fun chave_estrangeira(){
        val dao = db.especialidade()
        val md = db.medicoDao()
        dao.insert( Especialidade( "Ortopedista"))
        val orto = dao.getAll().first()
        val mario = Medico.instance(orto, "Mario Mario", null, null)
        val luigi = Medico.instance(orto, "Luigi Luigi", null, null)
        md.insert(mario)
        md.insert(luigi)

        val x = md.getAllMedicosEspecialidade()
        val medicos = x.map { it.medico }
        assert(medicos.any { it.firstName == mario.firstName })
        assert(medicos.any { it.firstName == luigi.firstName })

        val result = md.findMedEspec("Mario", "")
        assert(result.medico.firstName == mario.firstName){
            "Estava esperando o Mario, porem veio ${result.medico.firstName}"
        }

    }
}