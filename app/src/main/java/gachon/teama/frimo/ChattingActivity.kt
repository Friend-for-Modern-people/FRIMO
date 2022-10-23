package gachon.teama.frimo

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.google.firebase.database.*
import gachon.teama.frimo.databinding.ActivityChattingBinding
import java.util.*

// Todo: 00:00를 표현할 수 있는 방법에 대해 알아보기

class ChattingActivity : ComponentActivity() {

    // Binding
    private lateinit var binding: ActivityChattingBinding

    // Database
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    // Chatting
    private lateinit var userName: String
    private var chatList = mutableListOf<DataItem>() // Chatting 내역
    private var mAdapter = ChatAdapter(chatList)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Todo: user 정보 가져오기 -> firebase에서 해당 유저의 채팅 내역을 가져오기 위해
        userName = "namseunghyeon"

        with(binding) {

            // When back button clicked
            buttonBack.setOnClickListener {
                finish()
            }

            // Set recyclerview
            recyclerviewChatting.setHasFixedSize(true)
            recyclerviewChatting.adapter = mAdapter

            // When + button clicked
            // Todo: 키보드랑 화면 동시에 뜨는 현상 제거
            //  (참고) https://wooooooak.github.io/android/2020/07/30/emoticon_container/
            buttonPlus.setOnClickListener {
                if (viewSendData.isShown)
                    viewSendData.visibility = View.GONE
                else
                    viewSendData.visibility = View.VISIBLE
            }

            // When album button clicked
            buttonAlbum.setOnClickListener {

            }

            // When camera button clicked
            buttonCamera.setOnClickListener {

            }

            // When voice button clicked
            buttonVoice.setOnClickListener {

            }

            // When file button clicked
            buttonFile.setOnClickListener {

            }

            // When send button clicked
            buttonSend.setOnClickListener {

                // Send message
                val msg: String = edittextChat.text.toString()
                val chat = ChatDTO("Me", msg, Date())
                myRef.child(userName).child("chat").push().setValue(chat)
                    .addOnCompleteListener {
                        edittextChat.setText("")
                    }
            }

        }

        // DatabaseReference child event listener
        myRef.child(userName).child("chat").addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val chat: ChatDTO =
                    dataSnapshot.getValue(ChatDTO::class.java) ?: throw Error("load error")

                // Change data format (ChatDTO -> DataItem)
                val chatData: DataItem = if (chat.who == "Me")
                    DataItem(
                        chat.message,
                        "Me",
                        ChatWindowLocation.Right.content,
                        chat.time
                    )
                else
                    DataItem(
                        chat.message,
                        "FRIMO",
                        ChatWindowLocation.Left.content,
                        chat.time
                    )

                // add chat data in adapter
                mAdapter.addChat(chatData)

                // Update the chat window when you send a chat
                binding.recyclerviewChatting.scrollToPosition(chatList.size - 1)

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }

        })

    }
}