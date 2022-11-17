package gachon.teama.frimo.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    private lateinit var database: AppDatabase

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(this@SplashActivity)!!

        // Todo: DB에 값이 없는 경우 캐릭터 data 추가
        if(database.characterDao().getAll().isEmpty()){

        }

        // Delay screen
        Handler(Looper.getMainLooper()).postDelayed({

//            Log.d("ACT/SPLASH", database.userDao().getNickname())

            // If the user has set a nickname, go to the main page, otherwise go to the login page
            if(database.userDao().getNickname() != null) {
                startNextActivity(MainActivity::class.java)
            } else {
                startNextActivity(LoginActivity::class.java)
            }

        }, 3000)

    }
}