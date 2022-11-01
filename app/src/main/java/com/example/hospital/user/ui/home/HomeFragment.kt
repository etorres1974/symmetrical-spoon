package com.example.hospital.user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hospital.databinding.FragmentHomeBinding
import com.example.hospital.databinding.FragmentHomeUserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWelcome()
    }

    private fun setupWelcome(){
        val user = Firebase.auth.currentUser
        if(user == null){
            binding.tvWelcome.text = "Bem vindo Convidado"
        }else {
            binding.tvWelcome.text = "Bem vindo ${user?.displayName}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}