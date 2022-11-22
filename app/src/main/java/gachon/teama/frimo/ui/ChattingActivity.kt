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
import com.google.firebase.database.*
import gachon.teama.frimo.adapter.ChatAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.Chat
import gachon.teama.frimo.databinding.ActivityChattingBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// Todo: 00:00를 표현할 수 있는 방법에 대해 알아보기

class ChattingActivity : BaseActivity<ActivityChattingBinding>(ActivityChattingBinding::inflate) {

    // Database
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var database: AppDatabase

    // Chatting
    private lateinit var userName: String
    private var chatList = mutableListOf<Chat>() // Chatting 내역
    private var mAdapter = ChatAdapter(chatList)

    //Cam&Gallery
    private var DEFAULT_GALLERY_REQUEST_CODE = 1
    private var TAKE_PICTURE = 1

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(this@ChattingActivity)!!

        // Todo: 카카오 로그인 구현시 카카오 토큰으로 변경해 채팅내역 가져오기
        userName = "namseunghyeon"

        setEventListener()
        setRecyclerview()

    }

    private fun setEventListener() {

        // DatabaseReference child event listener
        myRef.child(userName).child("chat").addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val chat: Chat =
                    dataSnapshot.getValue(Chat::class.java) ?: throw Error("load error")

                // add chat data in adapter
                mAdapter.addChat(chat)

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

        // Set click listener
        setClickListener()

    }

    private fun setClickListener() {

        with(binding) {

            // When back button clicked
            buttonBack.setOnClickListener {

                // 최근 대화 날짜 저장
                val now = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd")) // 현재 날짜
                database.userDao().updateRecentlyChatDate(now)

                // 최근 대화 친구 저장
                val recentlyTalkFriendId = intent.getIntExtra("id", 99)
                database.userDao().updateRecentlyChatFriendId(recentlyTalkFriendId)

                finish()
            }

            // When find button clicked (basic layout에서 돋보기 버튼)
            buttonFind.setOnClickListener {
                layoutBasic.visibility = View.GONE
                layoutSearch.visibility = View.VISIBLE
            }

            // When search button clicked (search layout에서 돋보기 버튼)
            buttonSearch.setOnClickListener {

                var word = edittextSearch.text.toString()

                for (i in 1..chatList.size) {
                    // Todo: 단어 찾기
                }

            }

            textviewTextCancel.setOnClickListener {
                layoutBasic.visibility = View.VISIBLE
                layoutSearch.visibility = View.GONE
            }

            // When '+' button clicked
            buttonPlus.setOnClickListener {

                // Todo: 키보드랑 화면 동시에 뜨는 현상 제거
                //  (참고) https://wooooooak.github.io/android/2020/07/30/emoticon_container/

                if (layoutSendData.isShown) { // if it was already shown
                    layoutSendData.visibility = View.GONE
                    buttonPlus.animate().rotation(0f).setDuration(500).start()
                } else { // If it hasn't already been shown
                    layoutSendData.visibility = View.VISIBLE
                    buttonPlus.animate().rotation(45f).setDuration(500).start()
                }
            }

            // When send button clicked
            buttonSend.setOnClickListener {

                // Send message
                val msg: String = edittextChat.text.toString()
                val chat = Chat("Me", msg, Date())
                myRef.child(userName).child("chat").push().setValue(chat)
                    .addOnCompleteListener {
                        edittextChat.setText("")
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

        }

    }

    private fun setRecyclerview() {
        binding.recyclerviewChatting.setHasFixedSize(true)
        binding.recyclerviewChatting.adapter = mAdapter
    }

    private fun startDefalultGalleryApp() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            val uri = uri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, TAKE_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {

            DEFAULT_GALLERY_REQUEST_CODE -> {
                data ?: return
                val uri = data.data as Uri
            }
            TAKE_PICTURE -> {
                data ?: return
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