package gachon.teama.frimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.database.*
import gachon.teama.frimo.databinding.ActivityChattingBinding
import java.util.*

// Todo: 채팅을 보내면 채팅이 올라가게
// Todo: DataItem 없앨 수 있는 지 알아보기

class ChattingActivity : ComponentActivity() {

    // Binding
    private lateinit var binding: ActivityChattingBinding

    // Database
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    // Chatting
    private lateinit var CHAT_NAME : String
    private lateinit var userName: String
    private var chatList = mutableListOf<DataItem>() // Chatting 내역
    private var mAdapter = ChatAdapter(chatList)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Todo: 채팅방 이름, 유저 이름 어떻게 처리할 지 고민하기
        CHAT_NAME = "발표용"
        userName = "test"

        with(binding){

            // RecyclerView Setting
            binding.recyclerviewChatting.setHasFixedSize(true)
            binding.recyclerviewChatting.adapter = mAdapter

            // Send Button Click Listener
            ButtonSend.setOnClickListener {

                var msg: String = EditTextChat.text.toString()
                if (msg != null) {
                    var chat: ChatDTO = ChatDTO(userName, msg, Date())
                    myRef.child("chat").child(CHAT_NAME).push().setValue(chat).addOnCompleteListener {
                        EditTextChat.setText("")
                    }
                }
            }

        }

        myRef.child("chat").child(CHAT_NAME).addChildEventListener(object: ChildEventListener{

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val chat : ChatDTO = dataSnapshot.getValue(ChatDTO::class.java) ?: throw Error("load error")

                var chatData : DataItem
                if(chat.nickname.equals(userName))
                    chatData = DataItem(chat.message, chat.nickname, ChatWindowLocation.Right.content, chat.time)
                else
                    chatData = DataItem(chat.message, chat.nickname, ChatWindowLocation.Left.content, chat.time)

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

        });

    }
}