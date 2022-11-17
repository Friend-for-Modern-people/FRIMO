package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentSettingBinding

class SettingFragment : Fragment(){

    // Binding
    private lateinit var binding: FragmentSettingBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSettingBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        // Set user nickname
        binding.textviewNickname.text = database.userDao().getNickname()

        // Inflate the layout for this fragment
        return binding.root
    }
}