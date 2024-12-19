package com.example.gouvernorats

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 9001
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        callbackManager = CallbackManager.Factory.create()

        val googleSignInButton: Button = findViewById(R.id.google_sign_in_button)
        val facebookSignInButton: Button = findViewById(R.id.facebook_sign_in_button)
        val emailSignInButton: Button = findViewById(R.id.email_sign_in_button)
        val btnBack: ImageButton = findViewById(R.id.btnBack) // Ajout du bouton de retour

        // Configuration de Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Gestion du clic sur le bouton Google
        googleSignInButton.setOnClickListener {
            Log.d("LoginActivity", "Google Sign-In button clicked")
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // Gestion du clic sur le bouton Facebook
        facebookSignInButton.setOnClickListener {
            try {
                LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
                LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        Log.d("LoginActivity", "Facebook sign-in success: ${result.accessToken}")
                        navigateToNextScreen()
                    }

                    override fun onCancel() {
                        Log.d("LoginActivity", "Facebook sign-in canceled")
                    }

                    override fun onError(error: FacebookException) {
                        Log.e("LoginActivity", "Facebook sign-in error: ${error.message}", error)
                    }
                })
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error during Facebook login: ${e.message}", e)
            }
        }

        // Gestion du clic sur le bouton Email
        emailSignInButton.setOnClickListener {
            try {
                val intent = Intent(this, EmailSignInActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("LoginActivity", "Error starting EmailSignInActivity: ${e.message}", e)
            }
        }

        // Gestion du clic sur le bouton de retour
        btnBack.setOnClickListener {
            // Démarre l'activité de recherche
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("LoginActivity", "Google sign-in success: ${account.displayName}")
            navigateToNextScreen()
        } catch (e: ApiException) {
            Log.w("LoginActivity", "Google sign-in failed: ${e.statusCode}")
        }
    }

    private fun navigateToNextScreen() {
        // Naviguer vers MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Fermer l'activité de connexion pour éviter de revenir en arrière
    }
}

