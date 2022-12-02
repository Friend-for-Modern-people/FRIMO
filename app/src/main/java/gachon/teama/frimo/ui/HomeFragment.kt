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

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initVariable()
        setClickListener()
        setRecyclerview()

        return binding.root // Inflate the layout for this fragment
    }

    /**
     * @description - 생명주기 onResume
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun onResume() {
        super.onResume()
        setRecentlyTalkFriend()
    }

    /**
     * @description - 변수 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun initVariable() {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!
        friend = database.friendDao().getFriendList() as ArrayList
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener(){

        // Set button(my best friend) click listener
        binding.buttonMyBestFriend.setOnClickListener {
            startActivity(Intent(requireContext(), MyBestFriendActivity::class.java))
        }

        // Set layout(recently talk friend) click listener
        binding.layoutRecentlyTalkFriend.setOnClickListener {
            val intent = Intent(requireContext(), SetCharacterActivity::class.java)
            intent.putExtra("id", database.userDao().getRecentlyChatFriendId())
            startActivity(intent)
        }

        with(binding){

            // Set text(차분) click listener
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

            // Set text(친숙함) click listener
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

            // Set text(따뜻함) click listener
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

            // Set text(존경) click listener
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

    /**
     * @description - Set recyclerview
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview(){

        // Set recommend friend recyclerview
        binding.recyclerviewRecommendFriend.setHasFixedSize(true)
        binding.recyclerviewRecommendFriend.adapter = RecommendFriendsAdapter(friend)

        // Set friend recyclerview
        binding.recyclerviewFriend.setHasFixedSize(true)
        binding.recyclerviewFriend.adapter = FriendsAdapter(getTheme1Friend())

    }

    /**
     * @description - 화면에 최근에 대화한 친구 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecentlyTalkFriend(){

        // 대화 경험 여부 (대화를 한 적이 없는 경우 최근 친구 id는 99)
        var experience: Boolean = !(database.userDao().getRecentlyChatFriendId() == 99)

        if (experience) {

            val recently_talk = friend[database.userDao().getRecentlyChatFriendId() - 1] // 배열은 0부터 시작

            // layout에 최근 대화 친구 셋팅
            binding.imageviewRecentlyTalkFriend.setImageDrawable(getResources().getDrawable(recently_talk.img_theme))
            binding.textviewRecentlyTalkFriendName.text = recently_talk.name
            binding.textviewWhenTalked.text = database.userDao().getRecentlyChatDate()

        } else {
            binding.layoutRecentlyTalk.visibility = View.GONE
        }
    }

    /**
     * @description - '차분' 태그를 포함하는 친구만 추출
     * @param - None
     * @return - friend(ArrayList<Friend>) : '차분'의 태그를 포함하는 친구
     * @author - namsh1125
     */
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

    /**
     * @description - '친숙함' 태그를 포함하는 친구만 추출
     * @param - None
     * @return - friend(ArrayList<Friend>) : '친숙함'의 태그를 포함하는 친구
     * @author - namsh1125
     */
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

    /**
     * @description - '따뜻함' 태그를 포함하는 친구만 추출
     * @param - None
     * @return - friend(ArrayList<Friend>) : '따뜻함'의 태그를 포함하는 친구
     * @author - namsh1125
     */
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

    /**
     * @description - '존경' 태그를 포함하는 친구만 추출
     * @param - None
     * @return - friend(ArrayList<Friend>) : '존경'의 태그를 포함하는 친구
     * @author - namsh1125
     */
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