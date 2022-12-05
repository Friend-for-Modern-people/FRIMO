package gachon.teama.frimo.ui

import android.content.Intent
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySetCharacterBinding

class SetCharacterActivity : BaseActivity<ActivitySetCharacterBinding>(ActivitySetCharacterBinding::inflate) {

    private val database by lazy { AppDatabase.getInstance(this@SetCharacterActivity)!! }
    private lateinit var friend: Friend

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
    }

    /**
     * @description - 변수 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun initVariable() {

        // Get friend information
        var id = intent.getIntExtra("id", 1)
        friend = getFriend(id)
    }

    /**
     * @description - 친구 정보 가져오기
     * @param - id(Int) : 친구 id
     * @return - friend(Friend) : 친구 정보
     * @author - namsh1125
     */
    private fun getFriend(id: Int) : Friend {
        return database.friendDao().getFriend(id)
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
                database.friendDao().updateFriendLike(friend.id, friend.like) // Update DB
            }

            // Set start button click listener
            buttonChatStart.setOnClickListener {

                // Todo: 세팅된 캐릭터와 어떻게 채팅할지 고민해볼 것

                // Start chatting activity
                var intent = Intent(this@SetCharacterActivity, ChattingActivity::class.java)
                intent.putExtra("id", friend.id)
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

            imageviewFriendProfile.setImageDrawable(getDrawable(friend.img_profile))
            textviewFriendName.text = friend.name
            textviewFriendWarmthRate.text = friend.warmth.toString()
            textviewFriendSympathyRate.text = friend.sympathy.toString()
            textviewFriendIntroduction.text = friend.introduce
            imageviewFriendLive.setImageDrawable(getDrawable(friend.img_live))
            textviewFriendLive.text = friend.live
            imageviewFriendHeight.setImageDrawable(getDrawable(friend.img_height))
            textviewFriendHeight.text = friend.height
            imageviewFriendPrefer.setImageDrawable(getDrawable(friend.img_prefer))
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
            binding.imageButtonLike.background.setTint(resources.getColor(R.color.like))
        } else {
            binding.imageButtonLike.background.setTint(resources.getColor(R.color.unlike))
        }
    }

}