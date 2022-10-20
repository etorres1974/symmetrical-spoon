package com.example.hospital.ui.medico

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        }
    }

    fun editarEspecialidade(especialidade: Especialidade, novoNome : String){
        viewModelScope.launch(Dispatchers.IO) {
            especialidadeRepository.editar(especialidade.id, novoNome)
        }
    }

    fun removerMedico(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            medicoRepository.remover(id)
        }
    }

    fun removerEspecialiadde(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            especialidadeRepository.remover(id)
        }
    }

    fun setupInitialData(){
        viewModelScope.launch(Dispatchers.IO) {
            if(medicoRepository.getAll().isEmpty()) {
                val mockEspecialidades = listOf("Ortopedista", "Cardiologista")
                val mockMedicos = listOf("Clxa", "Marcelo Machado")
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
}