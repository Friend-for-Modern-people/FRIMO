package gachon.teama.frimo.ui

import android.content.Intent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySetCharacterBinding

class SetCharacterActivity : BaseActivity<ActivitySetCharacterBinding>(ActivitySetCharacterBinding::inflate) {

    // Database
    private val database by lazy { AppDatabase.getInstance(this@SetCharacterActivity)!! }

    // Friend
    private val id by lazy { intent.getIntExtra("id", 1) }
    private val friend by lazy { database.friendDao().getFriend(id) }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setScreen()
        setClickListener()
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        with(binding) {

            // Set back button click listener
            buttonBack.setOnClickListener {
                finish()
            }

            // Set like button click listener
            layoutLikeButton.setOnClickListener {

                friend.like = !friend.like // Update like
                setLike() // Update screen
                database.friendDao().updateFriendLike(id, friend.like) // Update DB
            }

            // Set start button click listener
            buttonChatStart.setOnClickListener {

                // Todo: 세팅된 캐릭터와 어떻게 채팅할지 고민해볼 것

                // Start chatting activity
                val intent = Intent(this@SetCharacterActivity, ChattingActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

        }
    }

    /**
     * @description - 화면에 친구 정보 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {

        setLike()

        with(binding) {

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
    }

    /**
     * @description - 친구를 좋아하는지 여부에 따라 화면(하트) 색상 변경
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setLike() {

        if (friend.like) {
            binding.imageButtonLike.background.setTint(ContextCompat.getColor(this, R.color.like))
        } else {
            binding.imageButtonLike.background.setTint(ContextCompat.getColor(this, R.color.unlike))
        }
    }

}