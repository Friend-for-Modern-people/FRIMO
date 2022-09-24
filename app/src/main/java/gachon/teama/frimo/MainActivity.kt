package gachon.teama.frimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icChat.setOnClickListener {
            startActivity(Intent(this, ChattingActivity::class.java))
        }

    }
}