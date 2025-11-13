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
            if (session.userId > 0 && session.accessToken.isNotBlank()) {
                com.example.levelupprueba.data.remote.ApiConfig.setAuthToken(session.accessToken)
                com.example.levelupprueba.data.remote.ApiConfig.setUserId(session.userId.toString())
                // Usuario logueado: ir a MainActivity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                com.example.levelupprueba.data.remote.ApiConfig.clear()
                // No logueado: ir a AuthActivity
                val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                intent.putExtra("startDestination", "welcome")
                startActivity(intent)
            }
            finish()
        }
    }
}