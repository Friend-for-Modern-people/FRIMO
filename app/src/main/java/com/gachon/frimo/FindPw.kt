package com.gachon.frimo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem

class FindPw : AppCompatActivity() {

    private lateinit var menu : PowerMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_pw)

        // 기존 action bar 제거 후 custom action bar 넣기
        supportActionBar!!.hide()

        // menu icon click listener
        val icon = findViewById<ImageView>(R.id.ic_menu)
        icon.setOnClickListener {
                view -> menu.showAsDropDown(view)
        }

        var button_findPw: Button = findViewById(R.id.button_findPw)
        menu = PowerMenu.Builder(this)
            .addItem(PowerMenuItem("Find Id", false))
            .addItem(PowerMenuItem("Find Pw", true))
            .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT) // popup시 menu가 뜨는 위치
            .setTextColor(Color.BLACK)
            .setTextGravity(Gravity.CENTER) // 글자 위치
            .setSelectedMenuColor(getResources().getColor(R.color.purple_200))
            .setSelectedTextColor(Color.WHITE)
            .setOnMenuItemClickListener(changeMenuItemClickListener)
            .build()

        button_findPw.setOnClickListener{
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    // change menu item click listener
    private val changeMenuItemClickListener =
        OnMenuItemClickListener<PowerMenuItem> { position, item ->
            Toast.makeText(baseContext, item.title, Toast.LENGTH_SHORT).show()
            val intent: Intent
            when (position) {
                0 -> {
                    intent = Intent(this, FindId::class.java)
                    startActivity(intent)
                }
            }
        }
}