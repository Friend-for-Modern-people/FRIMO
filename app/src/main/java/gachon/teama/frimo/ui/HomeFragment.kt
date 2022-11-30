package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.FriendsAdapter
import gachon.teama.frimo.adapter.RecommendFriendsAdapter
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentHomeBinding
import java.util.function.Predicate

class HomeFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentHomeBinding

    // Database
    private lateinit var database: AppDatabase

    // Friend
    private lateinit var friend: ArrayList<Friend>

    // RecyclerView
    private lateinit var recommendFriendAdapter: RecommendFriendsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initVariable()
        setClickListener()
        setRecyclerview()

        return binding.root // Inflate the layout for this fragment
    }

    override fun onResume() {
        super.onResume()
        setRecentlyTalkFriend()
    }

    private fun initVariable() {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!
        friend = database.friendDao().getFriendList() as ArrayList
        recommendFriendAdapter = RecommendFriendsAdapter(friend)
    }

    private fun setClickListener(){

        // When my best friend button clicked
        binding.buttonMyBestFriend.setOnClickListener {
            startActivity(Intent(requireContext(), MyBestFriendActivity::class.java))
        }

        // When recently talk friend layout clicked
        binding.layoutRecentlyTalkFriend.setOnClickListener {
            val intent = Intent(requireContext(), SetCharacterActivity::class.java)
            intent.putExtra("id", database.userDao().getRecentlyChatFriendId())
            startActivity(intent)
        }

        with(binding){

            // When theme1(차분) clicked
            binding.textviewTheme1.setOnClickListener {

                textviewFriendCount.text = getTheme1Friend().size.toString()
                textviewFriendTag.text = "차분한"
                recyclerviewFriend.adapter = FriendsAdapter(getTheme1Friend())

                // 테두리 변경
                textviewTheme1.background = resources.getDrawable(R.drawable.shape_border_fac883)
                textviewTheme1.setTextColor(resources.getColor(R.color.skin))
                textviewTheme2.background = null
                textviewTheme2.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme3.background = null
                textviewTheme3.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme4.background = null
                textviewTheme4.setTextColor(resources.getColor(R.color.gray5))

            }

            // When theme2(친숙함) clicked
            binding.textviewTheme2.setOnClickListener {

                textviewFriendCount.text = getTheme2Friend().size.toString()
                textviewFriendTag.text = "친숙한"
                recyclerviewFriend.adapter = FriendsAdapter(getTheme2Friend())

                // 테두리 변경
                textviewTheme1.background = null
                textviewTheme1.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme2.background = resources.getDrawable(R.drawable.shape_border_fac883)
                textviewTheme2.setTextColor(resources.getColor(R.color.skin))
                textviewTheme3.background = null
                textviewTheme3.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme4.background = null
                textviewTheme4.setTextColor(resources.getColor(R.color.gray5))

            }

            // When theme3(따뜻함) clicked
            binding.textviewTheme3.setOnClickListener {

                textviewFriendCount.text = getTheme3Friend().size.toString()
                textviewFriendTag.text = "따뜻한"
                recyclerviewFriend.adapter = FriendsAdapter(getTheme3Friend())

                // 테두리 변경
                textviewTheme1.background = null
                textviewTheme1.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme2.background = null
                textviewTheme2.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme3.background = resources.getDrawable(R.drawable.shape_border_fac883)
                textviewTheme3.setTextColor(resources.getColor(R.color.skin))
                textviewTheme4.background = null
                textviewTheme4.setTextColor(resources.getColor(R.color.gray5))

            }

            // When theme4(존경) clicked
            binding.textviewTheme4.setOnClickListener {

                textviewFriendCount.text = getTheme4Friend().size.toString()
                textviewFriendTag.text = "존경하는"
                recyclerviewFriend.adapter = FriendsAdapter(getTheme4Friend())

                // 테두리 변경
                textviewTheme1.background = null
                textviewTheme1.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme2.background = null
                textviewTheme2.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme3.background = null
                textviewTheme3.setTextColor(resources.getColor(R.color.gray5))
                textviewTheme4.background = resources.getDrawable(R.drawable.shape_border_fac883)
                textviewTheme4.setTextColor(resources.getColor(R.color.skin))
            }
        }
    }

    private fun setRecyclerview(){

        with(binding) {

            // Set recommend friend recyclerview
            recyclerviewRecommendFriend.setHasFixedSize(true)
            recyclerviewRecommendFriend.adapter = recommendFriendAdapter

            // Set friend recyclerview
            recyclerviewFriend.setHasFixedSize(true)
            recyclerviewFriend.adapter = FriendsAdapter(getTheme1Friend())

        }

    }

    private fun setRecentlyTalkFriend(){

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

    private fun getTheme1Friend() : ArrayList<Friend> {

        // Get friend
        var theme1Friend = friend.toMutableList()

        // Remove untagged friends
        var notTheme1 = Predicate<Friend> { friend: Friend ->
            !friend.tag.contains("#차분")
        }
        theme1Friend.removeIf(notTheme1)

        // Return theme1(차분) friend
        return theme1Friend as ArrayList<Friend>

    }

    private fun getTheme2Friend() : ArrayList<Friend> {

        // Get friend
        var friend = database.friendDao().getFriendList().toMutableList()

        // Remove untagged friends
        var notTheme2 = Predicate<Friend> { friend: Friend ->
            !friend.tag.contains("#친숙함")
        }
        friend.removeIf(notTheme2)

        // Return theme2(친숙함) friend
        return friend as ArrayList<Friend>

    }

    private fun getTheme3Friend() : ArrayList<Friend> {

        // Get friend
        var friend = database.friendDao().getFriendList().toMutableList()

        // Remove untagged friends
        var notTheme3 = Predicate<Friend> { friend: Friend ->
            !friend.tag.contains("#따뜻함")
        }
        friend.removeIf(notTheme3)

        // Return theme3(따뜻함) friend
        return friend as ArrayList<Friend>

    }

    private fun getTheme4Friend() : ArrayList<Friend> {

        // Get friend
        var friend = database.friendDao().getFriendList().toMutableList()

        // Remove untagged friends
        var notTheme4 = Predicate<Friend> { friend: Friend ->
            !friend.tag.contains("#존경")
        }
        friend.removeIf(notTheme4)

        // Return theme4(존경) friend
        return friend as ArrayList<Friend>

    }

}