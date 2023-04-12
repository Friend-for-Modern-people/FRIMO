package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.FriendsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityMyBestFriendBinding

class MyBestFriendActivity : BaseActivity<ActivityMyBestFriendBinding>(ActivityMyBestFriendBinding::inflate) {

    private val friendDao = AppDatabase.getInstance(this).friendDao()

    // 좋아요 누른 친구 리스트
    private val likeFriend by lazy { friendDao.getFriendList().filter { it.like } }

    override fun initAfterBinding() {
        binding.textviewBestFriendCount.text = "${likeFriend.size}" // 좋아요 누른 친구 숫자 설정
        setRecyclerview()
        setClickListener()
    }

    private fun setRecyclerview() {
        binding.recyclerviewMyBestFriend.setHasFixedSize(true)
        binding.recyclerviewMyBestFriend.adapter = FriendsAdapter(likeFriend)
    }

    private fun setClickListener() = with(binding) {
        buttonBack.setOnClickListener { finish() }
    }
}