package gachon.teama.frimo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash);

        // Delay screen
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },DURATION)

    }

    companion object {
        private const val DURATION : Long = 3000
    }
}