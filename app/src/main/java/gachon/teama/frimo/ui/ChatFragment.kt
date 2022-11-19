package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentChatBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentChatBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {

        super.onResume()

        var experience: Boolean = !(database.userDao().getRecentlyChatCharacterId() == 0 && database.userDao().getRecentlyChatDate().equals(""))

        if (experience) { // If there is a character the user has talked to recently

        } else {
            binding.layoutRecentlyTalk.visibility = View.GONE
        }
    }

}