package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentSettingBinding

/***
 * @see ChangeNicknameActivity
 * @see GuideActivity
 */

class SettingFragment : Fragment(){

    // Binding
    private lateinit var binding: FragmentSettingBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSettingBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        // When change nickname button clicked
        binding.buttonChangeNickname.setOnClickListener {
            startActivity(Intent(requireContext(), ChangeNicknameActivity::class.java))
        }

        // When notice layout clicked
        binding.layoutNotice.setOnClickListener {

        }

        // When guide layout clicked
        binding.layoutGuide.setOnClickListener {
            startActivity(Intent(requireContext(), GuideActivity::class.java))
        }

        // When logout button clicked
        binding.buttonLogout.setOnClickListener {

        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {

        super.onResume()

        // Set user nickname
        binding.textviewNickname.text = database.userDao().getNickname()
    }
}