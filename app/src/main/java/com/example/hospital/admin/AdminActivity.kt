package com.example.hospital.admin

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hospital.R
import com.example.hospital.databinding.ActivityMainBinding
import com.example.hospital.shared.ui.medico.MedicoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val medicoViewModel: MedicoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_medico, R.id.navigation_especialidade
            )
        )
        medicoViewModel.setupInitialData()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId)
            return@setOnItemSelectedListener true
        }
    }
}