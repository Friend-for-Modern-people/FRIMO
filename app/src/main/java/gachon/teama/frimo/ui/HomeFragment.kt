package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    // Database
    private val database by lazy { AppDatabase.getInstance(requireContext())!! }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

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
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

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

        with(binding) {

            // Set text(차분) click listener
            binding.textviewTheme1.setOnClickListener {

                textviewFriendCount.text = getFriend("차분").size.toString()
                textviewFriendTag.text = "차분한"
                recyclerviewFriend.adapter = FriendsAdapter(getFriend("차분"))

                // 테두리 변경
                textviewTheme1.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                textviewTheme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                textviewTheme2.background = null
                textviewTheme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme3.background = null
                textviewTheme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme4.background = null
                textviewTheme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
            }

            // Set text(친숙함) click listener
            binding.textviewTheme2.setOnClickListener {

                textviewFriendCount.text = getFriend("친숙함").size.toString()
                textviewFriendTag.text = "친숙한"
                recyclerviewFriend.adapter = FriendsAdapter(getFriend("친숙함"))

                // 테두리 변경
                textviewTheme1.background = null
                textviewTheme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme2.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                textviewTheme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                textviewTheme3.background = null
                textviewTheme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme4.background = null
                textviewTheme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
            }

            // Set text(따뜻함) click listener
            binding.textviewTheme3.setOnClickListener {

                textviewFriendCount.text = getFriend("따뜻함").size.toString()
                textviewFriendTag.text = "따뜻한"
                recyclerviewFriend.adapter = FriendsAdapter(getFriend("따뜻함"))

                // 테두리 변경
                textviewTheme1.background = null
                textviewTheme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme2.background = null
                textviewTheme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme3.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                textviewTheme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                textviewTheme4.background = null
                textviewTheme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
            }

            // Set text(존경) click listener
            binding.textviewTheme4.setOnClickListener {

                textviewFriendCount.text = getFriend("존경").size.toString()
                textviewFriendTag.text = "존경하는"
                recyclerviewFriend.adapter = FriendsAdapter(getFriend("존경"))

                // 테두리 변경
                textviewTheme1.background = null
                textviewTheme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme2.background = null
                textviewTheme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme3.background = null
                textviewTheme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
                textviewTheme4.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                textviewTheme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
            }
        }
    }

    /**
     * @description - Set recyclerview
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview() {

        setRecommendFriendRecyclerview()
        setFriendRecyclerview()
    }

    /**
     * @description - Set recommend friend recyclerview
     * @see gachon.teama.frimo.adapter.RecommendFriendsAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecommendFriendRecyclerview() {

        binding.recyclerviewRecommendFriend.setHasFixedSize(true)
        binding.recyclerviewRecommendFriend.adapter = RecommendFriendsAdapter(getAllFriend())
    }

    /**
     * @description - Set friend recyclerview
     * @see gachon.teama.frimo.adapter.FriendsAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setFriendRecyclerview() {

        binding.recyclerviewFriend.setHasFixedSize(true)
        binding.recyclerviewFriend.adapter = FriendsAdapter(getFriend("차분"))
    }

    /**
     * @description - 최근에 대화한 친구 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecentlyTalkFriend() {

        val recentlyTalkFriend = getRecentlyTalkFriend()

        if (recentlyTalkFriend != null) {

            // layout 셋팅
            binding.imageviewRecentlyTalkFriend.setImageDrawable(ResourcesCompat.getDrawable(resources, recentlyTalkFriend.img_theme, null))
            binding.textviewRecentlyTalkFriendName.text = recentlyTalkFriend.name
            binding.textviewWhenTalked.text = database.userDao().getRecentlyChatDate()

        } else {
            binding.layoutRecentlyTalk.visibility = View.GONE
        }
    }

    /**
     * @description - 저장된 모든 친구를 가지고 오는 함수
     * @param - None
     * @return - friend(ArrayList<Friend>) : 저장된 모든 친구
     * @author - namsh1125
     */
    private fun getAllFriend(): ArrayList<Friend> {
        return database.friendDao().getFriendList() as ArrayList
    }

    /**
     * @description - 찾고자 하는 특성을 포함하는 친구만 추출
     * @param - properties(String) : 찾고자 하는 친구의 특징
     * @return - friend(ArrayList<Friend>) : 해당 특징을 가지고 있는 친구
     * @author - namsh1125
     */
    private fun getFriend(properties: String): ArrayList<Friend> {

        // Get friend
        val friend = database.friendDao().getFriendList() as MutableList

        // 해당 특성을 가지고 있지 않은 친구들
        val noProperties = Predicate<Friend> { friend: Friend ->
            !friend.tag.contains(properties)
        }
        friend.removeIf(noProperties)

        // Return theme1(차분) friend
        return friend as ArrayList<Friend>

    }

    /**
     * @description - 최근에 대화한 친구를 가져오는 함수
     * @param - None
     * @return - friend(Friend) : 최근에 대화한 친구
     * @author - namsh1125
     */
    private fun getRecentlyTalkFriend(): Friend? {

        val recentlyTalkFriendId = database.userDao().getRecentlyChatFriendId()

        return if (recentlyTalkFriendId != 99) {
            database.friendDao().getFriend(recentlyTalkFriendId)
        } else {
            null
        }

    }
}