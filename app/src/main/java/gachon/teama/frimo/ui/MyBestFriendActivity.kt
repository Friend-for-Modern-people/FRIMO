package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.FriendsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityMyBestFriendBinding

class MyBestFriendActivity : BaseActivity<ActivityMyBestFriendBinding>(ActivityMyBestFriendBinding::inflate) {

    // Database
    private val database by lazy { AppDatabase.getInstance(this)!! }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setScreen()
        setClickListener()
        setRecyclerview()
    }

    /**
     * @description - 화면 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() = with(binding) {
        textviewBestFriendCount.text = getLikeFriend().size.toString()
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() = with(binding) {
        buttonBack.setOnClickListener { // Set back button click listener
            finish()
        }
    }

    /**
     * @description - Set recyclerview
     * @see gachon.teama.frimo.adapter.FriendsAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview() = with(binding) {
        recyclerviewMyBestFriend.setHasFixedSize(true)
        recyclerviewMyBestFriend.adapter = FriendsAdapter(getLikeFriend())
    }

    /**
     * @description - 좋아요 누른 친구 가져오기
     * @param - None
     * @return - friend(List<Friend>) : 좋아요 누른 친구
     * @author - namsh1125
     */
    private fun getLikeFriend() : List<Friend> {
        return database.friendDao().getFriendList().filter {
            it.like
        }
    }
}