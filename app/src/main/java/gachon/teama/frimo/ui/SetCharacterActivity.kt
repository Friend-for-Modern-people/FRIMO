package gachon.teama.frimo.ui

import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySetCharacterBinding

class SetCharacterActivity : BaseActivity<ActivitySetCharacterBinding>(ActivitySetCharacterBinding::inflate) {

    private lateinit var database: AppDatabase
    private lateinit var recently_talk_friend: Friend

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(this@SetCharacterActivity)!!
        recently_talk_friend = database.friendDao().getFriend(intent.getIntExtra("id",1))

        initScreen()

        with(binding){

            // When back button clicked
            buttonBack.setOnClickListener {
                finish()
            }

            // When like button clicked
            layoutLikeButton.setOnClickListener {

                recently_talk_friend.like = !recently_talk_friend.like // Update like
                setLike() // Update screen
                database.friendDao().updateFriendLike(recently_talk_friend.id, recently_talk_friend.like) // Update DB

            }

            // When chat start button clicked
            buttonChatStart.setOnClickListener {

                // Todo: 세팅된 캐릭터와 어떻게 채팅할지 고민해볼 것

                startNextActivity(ChattingActivity::class.java)
            }

        }

    }

    private fun initScreen() {

        with(binding) {

            setLike()

            imageviewFriendProfile.setImageDrawable(getDrawable(recently_talk_friend.img_profile))
            textviewFriendName.text = recently_talk_friend.name
            textviewFriendWarmthRate.text = recently_talk_friend.warmth.toString()
            textviewFriendSympathyRate.text = recently_talk_friend.sympathy.toString()
            textviewFriendIntroduction.text = recently_talk_friend.introduce
            imageviewFriendLive.setImageDrawable(getDrawable(recently_talk_friend.img_live))
            textviewFriendLive.text = recently_talk_friend.live
            imageviewFriendHeight.setImageDrawable(getDrawable(recently_talk_friend.img_height))
            textviewFriendHeight.text = recently_talk_friend.height
            imageviewFriendPrefer.setImageDrawable(getDrawable(recently_talk_friend.img_prefer))
            textviewFriendPrefer.text = recently_talk_friend.prefer

        }
    }

    private fun setLike(){

        if (recently_talk_friend.like) {
            binding.imageButtonLike.background.setTint(resources.getColor(R.color.like))
        } else {
            binding.imageButtonLike.background.setTint(resources.getColor(R.color.unlike))
        }
    }

}