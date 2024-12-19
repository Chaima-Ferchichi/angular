package com.example.gouvernorats

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.logging.Handler

class IntroActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)
        // Pour enlever la ActionBar dans MainActivity
        supportActionBar?.hide()


        // Délai de 2 secondes avant de passer à MainActivity
        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Empêche de revenir à l'écran Splash
        }, 2000) // Délai de 2 secondes
    }
}