package gachon.teama.frimo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.*
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

    // STT
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

//    //Cam&Gallery
//    private var DEFAULT_GALLERY_REQUEST_CODE = 1
//    private var TAKE_PICTURE = 1

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(this@ChattingActivity)!!

        // Todo: 카카오 로그인 구현시 카카오 토큰으로 변경해 채팅내역 가져오기
        userName = "namseunghyeon"

        // STT init
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        setListener()

        setDatabaseListener()
        setRecyclerview()
        setClickListener()

    }

    private fun setDatabaseListener() {

        // DatabaseReference child event listener
        myRef.child(userName).child("chat").addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val chat: Chat =
                    dataSnapshot.getValue(Chat::class.java) ?: throw Error("load error")

                // add chat data in adapter
                mAdapter.addChat(chat)

                Log.d("chat", chatList.toString())

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
            // Fixme: 채팅 내용 중 딱 한 개의 채팅만 존재하는 경우만 의도하는 대로 동작. 해결 필요
            buttonSearch.setOnClickListener {

                // Keyboard 숨기기
                hideKeyboard(it)

                var word = edittextSearch.text.toString()
                var position: MutableList<Int> = mutableListOf()

                // 찾는 단어의 위치들
                for (i in 0..chatList.size - 1) {
                    if (chatList[i].message.contains(word)) {
                        position.add(i)

                        val smoothScroller: RecyclerView.SmoothScroller by lazy {
                            object : LinearSmoothScroller(this@ChattingActivity) {
                                override fun getVerticalSnapPreference() = SNAP_TO_START
                            }
                        }
                        smoothScroller.targetPosition = i
                        recyclerviewChatting.layoutManager?.startSmoothScroll(smoothScroller)
                    }
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

            // 기획 변경으로 다음 버젼에서 출시
            // When album button clicked
            buttonAlbum.setOnClickListener {
                // Todo: Album
                showToast("추후 업데이트 예정입니다 :)")
                // startDefalultGalleryApp()
            }

            // When camera button clicked
            buttonCamera.setOnClickListener {
                // Todo: Camera
                showToast("추후 업데이트 예정입니다 :)")
//                openCamera()
            }

            // When voice button clicked
            buttonVoice.setOnClickListener {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
                speechRecognizer.setRecognitionListener(recognitionListener)
                speechRecognizer.startListening(intent)
            }

        }

    }

    private fun setRecyclerview() {
        binding.recyclerviewChatting.setHasFixedSize(true)
        binding.recyclerviewChatting.adapter = mAdapter
    }

//    private fun startDefalultGalleryApp() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
//    }
//
//    private fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//        createImageUri(newFileName(), "image/jpg")?.let { uri ->
//            val uri = uri.toString()
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//            startActivityForResult(intent, TAKE_PICTURE)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode != Activity.RESULT_OK) {
//            return
//        }
//
//        when (requestCode) {
//
//            DEFAULT_GALLERY_REQUEST_CODE -> {
//                data ?: return
//                val uri = data.data as Uri
//            }
//            TAKE_PICTURE -> {
//                data ?: return
//                val curi = data.data as Uri
//
//                curi.let { uri ->
////                    사진 프리뷰 들어갈 자리
//                }
//            }
//            else -> {
//                Toast.makeText(this, "사진 로딩 실패", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun createImageUri(filename: String, mimeType: String): Uri? {
//        var values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
//        return this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//    }
//
//    private fun newFileName(): String {
//        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
//        val filename = sdf.format(System.currentTimeMillis())
//        return "$filename.jpg"
//    }

//    STT
    private fun setListener(){
        recognitionListener = object: RecognitionListener{
            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext,"Recording start", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {
//                TODO("Not yet implemented")
            }

            override fun onRmsChanged(rmsdB: Float) {
//                TODO("Not yet implemented")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
//                TODO("Not yet implemented")
            }

            override fun onEndOfSpeech() {
//                TODO("Not yet implemented")
            }

            override fun onError(error: Int) {
                var message: String
                when(error){
                    SpeechRecognizer.ERROR_AUDIO ->
                        message = "Audio Error"
                    SpeechRecognizer.ERROR_CLIENT ->
                        message = "Client Error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                        message = "No permissions"
                    SpeechRecognizer.ERROR_NETWORK ->
                        message = "Network Error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                        message = "Network TIMEOUT"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY ->
                        message = "Recognizer is busy"
                    SpeechRecognizer.ERROR_SERVER ->
                        message = "SERVER is weird"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                        message = "Speech Time Exceeded"
                    else ->
                        message = "Unknown Error"
                }
                Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                var matches: ArrayList<String>? = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    for(i in 0 until matches.size){

                        val text = binding.edittextChat.text.toString()

                        binding.edittextChat.setText(text + " "+ matches[i])
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
//                TODO("Not yet implemented")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
//                TODO("Not yet implemented")
            }

        }
    }
}