package com.shaadi.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shaadi.R
import com.shaadi.core.CoreActivity
import com.shaadi.databinding.ActivitySplashBinding
import com.shaadi.presentation.ui.user.UserActivity
import com.shaadi.utils.clearTaskAndOpenActivity

class SplashActivity : CoreActivity<ActivitySplashBinding>(layoutResId = R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            clearTaskAndOpenActivity(UserActivity::class.java)
        }, 1000)
    }
}