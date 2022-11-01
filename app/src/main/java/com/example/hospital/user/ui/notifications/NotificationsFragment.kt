package com.example.hospital.user.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hospital.databinding.FragmentNotificationsBinding
import com.example.hospital.login.LoginActivity
import com.firebase.ui.auth.AuthUI

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        logout()
        return root
    }

    private fun logout(){
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}