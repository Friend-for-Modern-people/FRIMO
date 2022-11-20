package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.adapter.RecommendFriendsAdapter
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentChatBinding

    // Database
    private lateinit var database: AppDatabase

    // Friend
    private lateinit var friend: ArrayList<Friend>

    // RecyclerView
    private lateinit var mAdapter: RecommendFriendsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initVariable()

        // When recently talk friend layout clicked
        binding.layoutRecentlyTalkFriend.setOnClickListener {
            val intent = Intent(it.context, SetCharacterActivity::class.java)
            intent.putExtra("id", database.userDao().getRecentlyChatFriendId())
            startActivity(intent)
        }

        with(binding) {

            // Set recommend friend recyclerview
            recyclerviewRecommendFriend.setHasFixedSize(true)
            recyclerviewRecommendFriend.adapter = mAdapter
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {

        super.onResume()

        // 대화 경험 여부 (대화를 한 적이 없는 경우 최근 친구 id는 99)
        var experience: Boolean = !(database.userDao().getRecentlyChatFriendId() == 99)

        if (experience) {

            val recently_talk = friend[database.userDao().getRecentlyChatFriendId() - 1] // 배열은 0부터 시작

            with(binding) {

                // Setting information in layout
                imageviewRecentlyTalkFriend.setImageDrawable(getResources().getDrawable(recently_talk.img_theme))
                textviewRecentlyTalkFriendName.text = recently_talk.name
                textviewWhenTalked.text = database.userDao().getRecentlyChatDate()

            }

        } else {
            binding.layoutRecentlyTalk.visibility = View.GONE
        }
    }

    private fun initVariable() {
        binding = FragmentChatBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!
        friend = database.friendDao().getFriendList() as ArrayList
        mAdapter = RecommendFriendsAdapter(friend)
    }

}