package com.example.hospital.user.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospital.databinding.FragmentDashboardBinding
import com.example.hospital.shared.data.medico.Medico
import com.example.hospital.shared.ui.home.MedicoAdapter
import com.example.hospital.shared.ui.home.MedicoListener
import com.example.hospital.shared.ui.medico.MedicoViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoritosFragment : Fragment(), MedicoListener {

    private lateinit var binding: FragmentDashboardBinding
    private val medicoViewModel : MedicoViewModel by sharedViewModel()
    private val adapter =  MedicoAdapter(this, false)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.rvMedicos.adapter = adapter
        binding.rvMedicos.layoutManager = LinearLayoutManager(requireContext() ,
            LinearLayoutManager.VERTICAL, false)
        medicoViewModel.meusMedicosLiveData.observe(viewLifecycleOwner){
            adapter.submit(it)
            binding.textView.isVisible = it.isNullOrEmpty()
        }
        val isConvidado = Firebase.auth.currentUser == null

        binding.textViewForbidden.isVisible = isConvidado
        binding.clContent.isVisible = !isConvidado

        medicoViewModel.medicoFavoritoLivedata.observe(viewLifecycleOwner){
            adapter.submitMedicoFavorito(it)
        }

        return binding.root
    }

    override fun openEdit(medico: Medico) {
        TODO("Not yet implemented")
    }

    override fun delete(medico: Medico) {
        TODO("Not yet implemented")
    }

    override fun favorite(medico: Medico, favorite: Boolean) {
        val user = Firebase.auth.currentUser
        if(!favorite){
            medicoViewModel.favoritar(user?.uid, medico.id)
        }
        else{
            medicoViewModel.desfavoritar(user?.uid, medico.id)
        }
    }

}