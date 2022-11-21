package gachon.teama.frimo.ui

import android.util.Log
import gachon.teama.frimo.adapter.FriendsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityMyBestFriendBinding
import java.util.function.Predicate

class MyBestFriendActivity : BaseActivity<ActivityMyBestFriendBinding>(ActivityMyBestFriendBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    // RecyclerView
    private lateinit var mAdapter: FriendsAdapter

    private lateinit var likeFriend: MutableList<Friend>

    override fun initAfterBinding() {

        initVariable()

        // When back button clicked
        binding.buttonBack.setOnClickListener {
            finish()
        }

        with(binding) {

            // Set number of my best friend
            textviewBestFriendCount.text = likeFriend.size.toString()

            // Set my best friend recyclerview
            recyclerviewMyBestFriend.setHasFixedSize(true)
            recyclerviewMyBestFriend.adapter = mAdapter
        }


    }

    private fun getLikeFriend() {

        likeFriend = database.friendDao().getFriendList().toMutableList()

        var unlikeFriend = Predicate<Friend> { friend: Friend ->
            friend.like.equals(false)
        }

        likeFriend.removeIf(unlikeFriend)

    }

    private fun initVariable(){

        database = AppDatabase.getInstance(this@MyBestFriendActivity)!!

        getLikeFriend()
        mAdapter = FriendsAdapter(likeFriend as ArrayList)
    }

}