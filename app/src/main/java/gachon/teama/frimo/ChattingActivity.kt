package gachon.teama.frimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.database.*
import gachon.teama.frimo.databinding.ActivityChattingBinding
import java.util.*

// Todo: 채팅을 보내면 채팅이 올라가게
// Todo: DataItem 없앨 수 있는 지 알아보기
// Todo: 00:00를 표현할 수 있는 방법에 대해 알아보기
// Todo: 처음 실행 시 채팅 맨 아래로

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

        // Todo: user name 가져오기
        userName = "namseunghyeon"

        with(binding){

            // Set recyclerview
            recyclerviewChatting.setHasFixedSize(true)
            recyclerviewChatting.adapter = mAdapter

            // When send button clicked
            buttonSend.setOnClickListener {

                // Send message
                val msg: String = edittextChat.text.toString()
                if (msg != null) {
                    val chat: ChatDTO = ChatDTO(userName, msg, Date())
                    myRef.child("chat").child(userName).push().setValue(chat).addOnCompleteListener {
                        edittextChat.setText("")
                    }
                }
            }

            // When back button clicked
            buttonBack.setOnClickListener {
                finish()
            }

        }

        // DatabaseReference child event listener
        myRef.child("chat").child(userName).addChildEventListener(object: ChildEventListener{

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val chat : ChatDTO = dataSnapshot.getValue(ChatDTO::class.java) ?: throw Error("load error")

                // Change data format (ChatDTO -> DataItem)
                val chatData : DataItem
                if(chat.nickname.equals(userName))
                    chatData = DataItem(chat.message, chat.nickname, ChatWindowLocation.Right.content, chat.time)
                else
                    chatData = DataItem(chat.message, chat.nickname, ChatWindowLocation.Left.content, chat.time)

                // add chat data in adapter
                mAdapter.addChat(chatData)

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