package gachon.teama.frimo.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.*
import gachon.teama.frimo.adapter.ChatAdapter
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

    //Cam&Gallery
    private var DEFAULT_GALLERY_REQUEST_CODE = 1
    private var TAKE_PICTURE = 1

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

            // When search button clicked
            buttonSearch.setOnClickListener {

                // Todo: 검색할 단어를 입력받을 공간 UI 구현

                for (i in 1..chatList.size){

                }

            }

            // Set recyclerview
            recyclerviewChatting.setHasFixedSize(true)
            recyclerviewChatting.adapter = mAdapter

            // When '+' button clicked
            buttonPlus.setOnClickListener {

                // Todo: 키보드랑 화면 동시에 뜨는 현상 제거
                //  (참고) https://wooooooak.github.io/android/2020/07/30/emoticon_container/

                if (viewSendData.isShown) { // if it was already shown
                    viewSendData.visibility = View.GONE
                    buttonPlus.animate().rotation(0f).setDuration(500).start()
                }
                else { // If it hasn't already been shown
                    viewSendData.visibility = View.VISIBLE
                    buttonPlus.animate().rotation(45f).setDuration(500).start()
                }
            }

            // When album button clicked
            buttonAlbum.setOnClickListener {
                startDefalultGalleryApp()
            }

            // When camera button clicked
            buttonCamera.setOnClickListener {
                openCamera()
            }

            // When voice button clicked
            buttonVoice.setOnClickListener {

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

    private fun startDefalultGalleryApp(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
    }

    private fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            val uri = uri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, TAKE_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            return
        }

        when(requestCode) {

            DEFAULT_GALLERY_REQUEST_CODE -> {
                data?:return
                val uri = data.data as Uri
            }
            TAKE_PICTURE -> {
                data?:return
                val curi = data.data as Uri

                curi.let { uri ->
//                    사진 프리뷰 들어갈 자리
                }
            }
            else -> {
                Toast.makeText(this, "사진 로딩 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }
    private fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }
}