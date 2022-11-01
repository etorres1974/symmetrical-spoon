package com.example.hospital.user

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hospital.R
import com.example.hospital.databinding.ActivityUserBinding
import com.example.hospital.login.LoginActivity
import com.firebase.ui.auth.AuthUI

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_user)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_sair -> {
                if(getIsGuest()){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener {
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getUserName() = this.intent.getStringExtra(LoginActivity.USER_NAME)
    fun getUserEmail() = this.intent.getStringExtra(LoginActivity.USER_EMAIL)
    private fun getIsGuest() = this.intent.getBooleanExtra(LoginActivity.USER_GUEST, false)
}