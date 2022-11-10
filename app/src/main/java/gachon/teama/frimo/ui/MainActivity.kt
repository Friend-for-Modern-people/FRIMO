package gachon.teama.frimo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import gachon.teama.frimo.R
import gachon.teama.frimo.databinding.ActivityMainBinding

private const val TAG_CHAT = "chatting"
private const val TAG_DIARY = "diary"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_CHAT, chatting())

//        하단 네비게이션 바 클릭시 전환
        binding.navigationBar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.chatFragment -> setFragment(TAG_CHAT, chatting())
                R.id.diaryFragment -> setFragment(TAG_DIARY, diary())
            }
            true
        }

//        binding.icChat.setOnClickListener {
//            startActivity(Intent(this, ChattingActivity::class.java))
//        }
    }

//    프래그번트 전환 function
    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragmentTransaction = manager.beginTransaction()

        if(manager.findFragmentByTag(tag)==null){
            fragmentTransaction.add(R.id.frame, fragment, tag)
        }

        val chat = manager.findFragmentByTag(TAG_CHAT)
        val diary = manager.findFragmentByTag(TAG_DIARY)

        //Transaction.hide
        if(chat != null){
            fragmentTransaction.hide(chat)
        }
        if (diary != null){
            fragmentTransaction.hide(diary)
        }

        //Transaction.show
        if(tag== TAG_CHAT){
            if(chat!=null){
                fragmentTransaction.show(chat)
            }
        }
        else if(tag== TAG_DIARY){
            if(diary!=null){
                fragmentTransaction.show(diary)
            }
        }

        fragmentTransaction.commitAllowingStateLoss()
    }
}