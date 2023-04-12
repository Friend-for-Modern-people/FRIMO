package gachon.teama.frimo.ui

import android.content.Intent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.local.Friend
import gachon.teama.frimo.databinding.ActivitySetCharacterBinding

class SetCharacterActivity : BaseActivity<ActivitySetCharacterBinding>(ActivitySetCharacterBinding::inflate) {

    private val database: AppDatabase by lazy { AppDatabase.getInstance(this@SetCharacterActivity) }
    private val id: Int by lazy { intent.getIntExtra("id", 1) }
    private val friend: Friend by lazy { database.friendDao().getFriend(id) }

    override fun initAfterBinding() {
        setScreen()
        setClickListener()
    }

    private fun setClickListener() = binding.apply {
        buttonBack.setOnClickListener { finish() }

        layoutLikeButton.setOnClickListener {
            friend.like = !friend.like
            setLike()
            database.friendDao().updateFriendLike(id, friend.like)
        }

        buttonChatStart.setOnClickListener {
            val intent = Intent(this@SetCharacterActivity, ChattingActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun setScreen() = binding.apply {
        setLike()
        imageviewFriendProfile.setImageDrawable(AppCompatResources.getDrawable(this@SetCharacterActivity, friend.img_profile))
        textviewFriendName.text = friend.name
        textviewFriendWarmthRate.text = friend.warmth.toString()
        textviewFriendSympathyRate.text = friend.sympathy.toString()
        textviewFriendIntroduction.text = friend.introduce
        imageviewFriendLive.setImageDrawable(AppCompatResources.getDrawable(this@SetCharacterActivity, friend.img_live))
        textviewFriendLive.text = friend.live
        imageviewFriendHeight.setImageDrawable(AppCompatResources.getDrawable(this@SetCharacterActivity, friend.img_height))
        textviewFriendHeight.text = friend.height
        imageviewFriendPrefer.setImageDrawable(AppCompatResources.getDrawable(this@SetCharacterActivity, friend.img_prefer))
        textviewFriendPrefer.text = friend.prefer
    }

    private fun setLike() = binding.apply {
        val backgroundColor = if (friend.like) R.color.like else R.color.unlike
        imageButtonLike.background.setTint(ContextCompat.getColor(this@SetCharacterActivity, backgroundColor))
    }
}