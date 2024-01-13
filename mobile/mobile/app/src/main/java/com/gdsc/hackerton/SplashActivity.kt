package com.gdsc.hackerton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {

    //splash 화면을 띄우는 시간 설정
    private val SPLASH_TIMEOUT = 1000 // 1 seconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler(Looper.getMainLooper()).postDelayed({
            // 1초가 지난 후 띄울 화면 선택
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the splash activity to prevent going back to it
        }, SPLASH_TIMEOUT.toLong())
    }
}