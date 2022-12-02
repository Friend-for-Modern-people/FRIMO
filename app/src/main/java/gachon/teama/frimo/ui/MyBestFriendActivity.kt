package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.FriendsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityMyBestFriendBinding
import java.util.function.Predicate

class MyBestFriendActivity : BaseActivity<ActivityMyBestFriendBinding>(ActivityMyBestFriendBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        initVariable()
        setScreen()
        setClickListener()
        setRecyclerview()
    }

    /**
     * @description - 변수 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun initVariable(){
        database = AppDatabase.getInstance(this@MyBestFriendActivity)!!
    }

    /**
     * @description - 화면 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {
        binding.textviewBestFriendCount.text = getLikeFriend().size.toString()
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        // Set back button click listener
        binding.buttonBack.setOnClickListener {
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
    private fun setRecyclerview() {
        binding.recyclerviewMyBestFriend.setHasFixedSize(true)
        binding.recyclerviewMyBestFriend.adapter = FriendsAdapter(getLikeFriend())
    }

    /**
     * @description - 좋아요 누른 친구 가져오기
     * @param - None
     * @return - friend(ArrayList<Friend>) : 좋아요 누른 친구
     * @author - namsh1125
     */
    private fun getLikeFriend() : ArrayList<Friend> {

        val likeFriend = database.friendDao().getFriendList().toMutableList()

        val unlikeFriend = Predicate<Friend> { friend: Friend ->
            friend.like.equals(false)
        }

        likeFriend.removeIf(unlikeFriend)

        return likeFriend as ArrayList

    }

}