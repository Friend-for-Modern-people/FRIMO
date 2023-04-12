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
import gachon.teama.frimo.data.local.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    // Database
    private val database by lazy { AppDatabase.getInstance(requireContext()) }

    // Friends
    private val friends by lazy { database.friendDao().getFriendList() as ArrayList }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setClickListener()
        setRecyclerview()
        return binding.root // Inflate the layout for this fragment
    }

    override fun onResume() {
        super.onResume()
        setRecentlyTalkFriend()
    }

    private fun setClickListener() = with(binding) {

        buttonMyBestFriend.setOnClickListener {
            startActivity(Intent(requireContext(), MyBestFriendActivity::class.java))
        }

        layoutRecentlyTalkFriend.setOnClickListener {
            val intent = Intent(requireContext(), SetCharacterActivity::class.java)
            intent.putExtra("id", database.userDao().getRecentlyChatFriendId())
            startActivity(intent)
        }

        val themeClickListener = View.OnClickListener {
            when(it.id) {
                R.id.textview_theme1 -> {
                    resetFriendTheme("차분", "차분한")
                    textviewTheme1.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                    textviewTheme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                }
                R.id.textview_theme2 -> {
                    resetFriendTheme("친숙함", "친숙한")
                    textviewTheme2.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                    textviewTheme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                }
                R.id.textview_theme3 -> {
                    resetFriendTheme("따뜻함", "따뜻한")
                    textviewTheme3.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                    textviewTheme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                }
                R.id.textview_theme4 -> {
                    resetFriendTheme("존경", "존경하는")
                    textviewTheme4.background = ResourcesCompat.getDrawable(resources, R.drawable.shape_border_fac883, null)
                    textviewTheme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
                }
            }
        }

        textviewTheme1.setOnClickListener(themeClickListener)
        textviewTheme2.setOnClickListener(themeClickListener)
        textviewTheme3.setOnClickListener(themeClickListener)
        textviewTheme4.setOnClickListener(themeClickListener)
    }

    /**
     * @description - 친구 테마 초기화
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun resetFriendTheme(properties: String, tag: String) = with(binding) {

        // 클릭된 친구 테마에 맞게 화면 설정
        textviewFriendCount.text = getFriend(properties).size.toString()
        textviewFriendTag.text = tag
        recyclerviewFriend.adapter = FriendsAdapter(getFriend(properties))

        // 친구 테마 배경 초기화
        textviewTheme1.background = null
        textviewTheme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
        textviewTheme2.background = null
        textviewTheme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
        textviewTheme3.background = null
        textviewTheme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
        textviewTheme4.background = null
        textviewTheme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray5))
    }

    private fun setRecyclerview() = with(binding) {

        // recommend friend recyclerview
        recyclerviewRecommendFriend.setHasFixedSize(true)
        recyclerviewRecommendFriend.adapter = RecommendFriendsAdapter(friends)

        // friend recyclerview
        recyclerviewFriend.setHasFixedSize(true)
        recyclerviewFriend.adapter = FriendsAdapter(getFriend("차분"))
    }

    /**
     * @description - 최근에 대화한 친구 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecentlyTalkFriend() = with(binding) {

        val recentlyTalkFriendId = database.userDao().getRecentlyChatFriendId()

        if (recentlyTalkFriendId != 99) {
            val recentlyTalkFriend = database.friendDao().getFriend(recentlyTalkFriendId)

            // layout 셋팅
            imageviewRecentlyTalkFriend.setImageDrawable(ResourcesCompat.getDrawable(resources, recentlyTalkFriend.img_theme, null))
            textviewRecentlyTalkFriendName.text = recentlyTalkFriend.name
            textviewWhenTalked.text = database.userDao().getRecentlyChatDate()
            layoutRecentlyTalk.visibility = View.VISIBLE
        }
    }

    /**
     * @description - 찾고자 하는 특성을 포함하는 친구만 추출
     * @param - properties(String) : 찾고자 하는 친구의 특징
     * @return - friend(List<Friend>) : 해당 특징을 가지고 있는 친구
     * @author - namsh1125
     */
    private fun getFriend(properties: String): List<Friend> {
        return friends.filter { it.tag.contains(properties) }
    }
}