package com.example.hospital.ui.medico

import androidx.lifecycle.LiveData
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

    private val _medico = MutableLiveData<List<Medico>>()
    val medicoLivedata: LiveData<List<Medico>> = _medico

    private val _especialidades = MutableLiveData<List<Especialidade>>()
    val especialidadeLivedata: LiveData<List<Especialidade>> = _especialidades

    init {
        setupInitialData()
    }

    fun adicionarMedico(especialidade: Especialidade, fullName : String, telefone: String?, address: Address?){
        viewModelScope.launch(Dispatchers.IO) {
            medicoRepository.adicionar(especialidade, fullName, telefone, address)
        }
    }

    fun adicionarEspecialidade(nome : String){
        viewModelScope.launch(Dispatchers.IO) {
            especialidadeRepository.adicionar(nome)
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

    private fun setupInitialData(){
        viewModelScope.launch(Dispatchers.IO) {
            if(medicoRepository.getAll().isEmpty()) {
                val mockEspecialidades = listOf("Ortopedista", "Cardiologista")
                val mockMedicos = listOf("Cladio Pereira", "Marcelo Machado")
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
            _medico.postValue(medicoRepository.getAll())
            _especialidades.postValue(especialidadeRepository.getAll())
        }
    }

}