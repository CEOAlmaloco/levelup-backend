package com.example.levelupprueba

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.levelupprueba.data.local.getUserSessionFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val session = getUserSessionFlow(this@SplashActivity).first()
            if (session.userId.isNotBlank()) {
                // Usuario logueado: ir a MainActivity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                // No logueado: ir a AuthActivity
                val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                intent.putExtra("startDestination", "welcome")
                startActivity(intent)
            }
            finish()
        }
    }
}