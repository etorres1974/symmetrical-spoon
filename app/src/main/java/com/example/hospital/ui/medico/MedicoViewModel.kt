package com.example.hospital.ui.medico

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import com.example.hospital.data.especialidade.Especialidade
import com.example.hospital.data.medico.Address
import com.example.hospital.data.medico.Medico
import com.example.hospital.domain.EspecialidadeRepository
import com.example.hospital.domain.MedicoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicoViewModel(
    private val especialidadeRepository: EspecialidadeRepository,
    private val medicoRepository: MedicoRepository
) : ViewModel() {
    companion object {
        var counter = 0
    }

    init {
        counter += 1
        Log.d("Teste123", counter.toString())
    }
    val medicoLivedata = MutableLiveData<List<Medico>>()
    val query = MutableLiveData<String>("")
    val especQuery = MutableLiveData<Int>(0)
    val medicosFiltradosLiveData  : LiveData<List<Medico>> = MediatorLiveData<List<Medico>>().apply{
        var currentQuery = ""
        var currentList = emptyList<Medico>()
        var currentEspec = 0
        addSource(medicoLivedata){
            currentList = it
            value = filterMedicos(currentList, currentQuery, currentEspec)
        }
        addSource(query){
            currentQuery = it
            value = filterMedicos(currentList, currentQuery, currentEspec)
        }
        addSource(especQuery){
            currentEspec = it
            value = filterMedicos(currentList, currentQuery, currentEspec)
        }
    }
    val especialidadeLivedata = MutableLiveData<List<Especialidade>>()

    fun adicionarMedico(especialidade: Especialidade, fullName : String, telefone: String?, address: Address?){
        viewModelScope.launch(Dispatchers.IO) {
            medicoRepository.adicionar(especialidade, fullName, telefone, address)
            medicoLivedata.postValue(medicoRepository.getAll())
        }
    }

    fun adicionarEspecialidade(nome : String){
        viewModelScope.launch(Dispatchers.IO) {
            especialidadeRepository.adicionar(nome)
            val novaLista = especialidadeRepository.getAll()
            especialidadeLivedata.postValue(novaLista)
        }
    }

    fun editarMedico(id : Int, especialidade: Especialidade, fullName : String, telefone: String?, address: Address?){
        viewModelScope.launch(Dispatchers.IO) {
            medicoRepository.editar(id, especialidade, fullName, telefone, address)
            medicoLivedata.postValue(medicoRepository.getAll())
        }
    }

    fun editarEspecialidade(especialidade: Especialidade, novoNome : String){
        viewModelScope.launch(Dispatchers.IO) {
            especialidadeRepository.editar(especialidade.id, novoNome)
            especialidadeLivedata.postValue(especialidadeRepository.getAll())
        }
    }

    fun removerMedico(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            medicoRepository.remover(id)
            val medicos = medicoRepository.getAll()
            medicoLivedata.postValue(medicos)
        }
    }

    fun removerEspecialiadde(id : Int, onError : () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                especialidadeRepository.remover(id)
                especialidadeLivedata.postValue(especialidadeRepository.getAll())
            }catch (e : SQLiteConstraintException){
                onError()
            }
        }
    }

    fun setupInitialData(){
        viewModelScope.launch(Dispatchers.IO) {
            if(medicoRepository.getAll().isEmpty()) {
                val mockEspecialidades = listOf("Ortopedista", "Cardiologista")
                val mockMedicos = listOf("Pedro Pimenta", "Marcelo Machado")
                mockEspecialidades.forEachIndexed { index, especialidade ->
                    especialidadeRepository.adicionar(especialidade)
                    medicoRepository.adicionar(
                        especialidade = Especialidade(id = index + 1, especialidade),
                        fullName = mockMedicos[index],
                        null,
                        null
                    )
                }
            }
            val medicos = medicoRepository.getAll()
            medicoLivedata.postValue(medicos)
            especialidadeLivedata.postValue(especialidadeRepository.getAll())
        }
    }

    fun filterMedicos(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            this@MedicoViewModel.query.postValue(query)
        }
    }

    fun filterMedicos(list: List<Medico>?, query: String, especId : Int): List<Medico> {
        val byName = list?.filter { it.toString().contains(query, ignoreCase = true) } ?: emptyList<Medico>()
        return if(especId != 0)
            byName.filter { it.especId == especId }
        else
            byName
    }

    fun selecionarEspecialidadeFiltro(p2: Int) = viewModelScope.launch(Dispatchers.IO) {
        especQuery.postValue(p2)
    }
}