package com.example.hospital.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.hospital.admin.AdminActivity
import com.example.hospital.databinding.ActivityLoginBinding
import com.example.hospital.user.UserActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        ActivityResultCallback<FirebaseAuthUIAuthenticationResult> { onSignInResult(res) }
    }

    // Choose authentication providers
    private val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

    // Create and launch sign-in intent
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()


    private fun onSignInResult(res: FirebaseAuthUIAuthenticationResult) {
        Log.d("Teste123", "Firebase onSignInResult ${res}")
        val response = res.idpResponse
        if (res.resultCode == RESULT_OK) {
            registerFlow()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setup()

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        registerFlow()
    }

    private fun registerFlow(){
        val currentUser = auth.currentUser
        val isLogged = currentUser != null
        if(isLogged){
            Log.d("Teste123", currentUser?.log() ?: "Null")
            if (currentUser != null) {
                openApp(currentUser)
            }
        }else {
            signInLauncher.launch(signInIntent)
        }
    }

    private fun FirebaseUser.log() : String = "$displayName $email"

    private fun setup()= with(binding){
        buttonLogin.setOnClickListener {
            val email = etUser.text.toString()
            val password = etPass.text.toString()
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null)
                                openApp(user)
                        } else {
                            loginException(task.exception)
                        }
                    }
            }catch (e : Exception){
                loginException(e)
            }
        }
    }

    private fun loginException(e : Exception?){
        Log.d("Teste123", "signInWithEmail:failure", e)
        Toast.makeText(baseContext, "Authentication failed : ${e?.message}", Toast.LENGTH_SHORT).show()
    }

    private fun openApp(user : FirebaseUser) {
        val admin = user?.displayName?.contains("admin", true) ?: false
        val activity = if(admin) AdminActivity::class.java else UserActivity::class.java
        val intent = Intent(this, activity).apply {
            putExtra(USER_EMAIL, user.email)
            putExtra(USER_NAME, user.displayName)
        }
        startActivity(intent)
    }

    companion object {
        val USER_EMAIL = "UserEmail"
        val USER_NAME = "UserName"
    }


}