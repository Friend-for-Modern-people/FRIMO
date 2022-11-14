package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.User
import gachon.teama.frimo.data.local.AppDatabase

class SplashActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        database = AppDatabase.getInstance(this@SplashActivity)!!

        // Delay screen
        Handler(Looper.getMainLooper()).postDelayed({

            // If the user has set a nickname, go to the main page, otherwise go to the login page
            if(database.userDao().getNickname().isNotEmpty())
                startActivity(Intent(this, MainActivity::class.java))
            else
                startActivity(Intent(this, LoginActivity::class.java))

            finish()

        }, 3000)
    }
}