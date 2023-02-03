package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import com.google.firebase.database.*
import gachon.teama.frimo.adapter.ChatAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.Chat
import gachon.teama.frimo.databinding.ActivityChattingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// Todo: 00:00를 표현할 수 있는 방법에 대해 알아보기

class ChattingActivity : BaseActivity<ActivityChattingBinding>(ActivityChattingBinding::inflate) {

    // Database
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val database by lazy { AppDatabase.getInstance(this@ChattingActivity)!! }

    // Chatting
    private val userName by lazy { database.userDao().getNickname() }
    private var chatList = mutableListOf<Chat>() // Chatting 내역
    private var mAdapter = ChatAdapter(chatList)

    // Layout
    private var keyboardHeight = 0

    // STT
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognitionListener: RecognitionListener

//    // Cam & Gallery
//    private var DEFAULT_GALLERY_REQUEST_CODE = 1
//    private var TAKE_PICTURE = 1

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125, Hongsi-Taste
     */
    override fun initAfterBinding() {
        initVariable()
        setDatabaseListener()
        setRecyclerview()
        setClickListener()
        setSTTListener()
    }

    /**
     * @description - 변수 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125, Hongsi-Taste
     */
    private fun initVariable() {

        // STT init and language setting
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")

        //get keyboard height
        val view: View = binding.root
        var rootHeight = -1
        view.viewTreeObserver.addOnGlobalLayoutListener {
            if (rootHeight == -1) rootHeight = view.height

            val visibleFrameSize = Rect()
            view.getWindowVisibleDisplayFrame(visibleFrameSize)

            val heightExceptKeyboard = visibleFrameSize.bottom - visibleFrameSize.top
            if (heightExceptKeyboard < rootHeight) {
                keyboardHeight = rootHeight - heightExceptKeyboard
            }
        }
    }

    /**
     * @description - DatabaseReference event listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setDatabaseListener() {

        myRef.child(userName).child("chat").addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                val chat: Chat = dataSnapshot.getValue(Chat::class.java) ?: throw Error("load error")

                mAdapter.addChat(chat) // add chat data in adapter
                binding.recyclerviewChatting.scrollToPosition(chatList.size - 1) // Update chat window
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

    /**
     * @description - Set recyclerview
     * @see gachon.teama.frimo.adapter.ChatAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview() = with(binding) {
        recyclerviewChatting.setHasFixedSize(true)
        recyclerviewChatting.adapter = mAdapter
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125, Hongsi-Taste
     */
    private fun setClickListener() = with(binding) {

        // Set back button click listener
        buttonBack.setOnClickListener {

            // 최근 대화 날짜 저장
            val now = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd")) // 현재 날짜
            database.userDao().updateRecentlyChatDate(now)

            // 최근 대화 친구 저장
            val recentlyTalkFriendId = intent.getIntExtra("id", 99)
            database.userDao().updateRecentlyChatFriendId(recentlyTalkFriendId)

            finish()
        }

        // Set find button click listener (basic layout에서 돋보기 버튼)
        buttonFind.setOnClickListener {
            layoutBasic.visibility = View.GONE
            layoutSearch.visibility = View.VISIBLE
            layoutSendData.visibility = View.GONE
            hideKeyboard(it)
        }

        // Set search button click listener (search layout에서 돋보기 버튼)
        // Fixme: 현재 위치를 기준으로 더 위에 있는 내용을 찾는게 아니라, 맨 아래에서 찾음. recyclerview 위치 찾는 법 찾아야됨
        buttonSearch.setOnClickListener {
            hideKeyboard(it) // Keyboard 숨기기

            var position = -1
            var findFlag = false
            for (i in chatList.size downTo  0) {
                if (chatList[i].message.contains(edittextSearch.text.toString())) {
                    position = i
                    findFlag = true
                    break
                }
            }

            if(findFlag) {
                val smoothScroller: RecyclerView.SmoothScroller by lazy {
                    object : LinearSmoothScroller(this@ChattingActivity) {
                        override fun getVerticalSnapPreference() = SNAP_TO_START
                    }
                }

                smoothScroller.targetPosition = position
                recyclerviewChatting.layoutManager?.startSmoothScroll(smoothScroller)

            } else {
                showToast("찾는 단어가 없습니다!")
            }
        }

        // Set cancel text click listener
        textviewTextCancel.setOnClickListener {
            layoutBasic.visibility = View.VISIBLE
            layoutSearch.visibility = View.GONE
            hideKeyboard(it)
        }

        // Set '+' button click listener
        buttonPlus.setOnClickListener {

            layoutSendData.setHeight(keyboardHeight)

            // 키보드와 '+'팝업이 동시에 뜨는 현상 방지
            lifecycleScope.launch {
                if (layoutSendData.isShown) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    layoutSendData.visibility = View.GONE
//                        showKeyboard()
                    delay(100)
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    buttonPlus.animate().rotation(0f).setDuration(500).start()
                } else {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                    layoutSendData.visibility = View.VISIBLE
                    hideKeyboard(layoutSendData)
                    delay(100)
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    buttonPlus.animate().rotation(45f).setDuration(500).start()
                }
            }
        }

        // 입력창을 눌렀을때 팝업과 키보드가 같이 뜨는 현상 방지
        edittextChat.setOnClickListener { layoutSendData.visibility = View.GONE }

        // Send message
        buttonSend.setOnClickListener {
            val chat = Chat(who = "Me", message = edittextChat.text.toString(), time = Date())
            myRef.child(userName).child("chat").push().setValue(chat)
                .addOnCompleteListener {
                    edittextChat.setText("")
                }
        }

        // 기획 변경으로 다음 버젼에서 출시
        buttonAlbum.setOnClickListener {

            // startDefalultGalleryApp()
            // Todo: (Not now) Album 이동 및 이미지 불러오기
            showToast("추후 업데이트 예정입니다 :)")
        }

        // 기획 변경으로 다음 버젼에서 출시
        buttonCamera.setOnClickListener {

//                openCamera()
            // Todo: (Not now) Camera 작동 및 이미지 (저장)&불러오기
            showToast("추후 업데이트 예정입니다 :)")
        }

        // Set voice button click listener
        buttonVoice.setOnClickListener {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
            speechRecognizer.setRecognitionListener(recognitionListener)
            speechRecognizer.startListening(intent)
        }
    }

    /**
     * @description - STT listener
     * @param - None
     * @return - None
     * @author - Hongsi-Taste
     */
    private fun setSTTListener() {

        recognitionListener = object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                Toast.makeText(applicationContext, "Recording start", Toast.LENGTH_SHORT).show()
            }

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                val message = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio Error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client Error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "No permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network Error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network TIMEOUT"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognizer is busy"
                    SpeechRecognizer.ERROR_SERVER -> "SERVER is weird"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Speech Time Exceeded"
                    else -> "Unknown Error"
                }

                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }

            //STT 결과를 텍스트로 입력창에 출력
            override fun onResults(results: Bundle?) {

                val matches: ArrayList<String>? = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    for (i in 0 until matches.size) {
                        val text = binding.edittextChat.text.toString()
                        binding.edittextChat.setText("$text ${matches[i]}")
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

        }

    }

    /**
     * @description - extends View class and add setHeight fun.
     * @param - value:Int
     * @return - None
     * @author - Hongsi-Taste
     */
    private fun View.setHeight(value: Int) {

        val lp = layoutParams
        lp?.let {
            lp.height = value
            layoutParams = lp
        }
    }

//    /**
//     * @description - Gallery connection
//     * @param - None
//     * @return - None
//     * @author - Hongsi-Taste
//     */
//    private fun startDefalultGalleryApp() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
//    }
//
//    /**
//     * @description - Opening default camera
//     * @param - None
//     * @return - None
//     * @author - Hongsi-Taste
//     */
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
//    /**
//     * @description - getting data from other apps
//     * @param - requestCode: Int, resultCode: Int, data: Intent?
//     * @return - data or None
//     * @author - Hongsi-Taste
//     */
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
//    /**
//     * @description - giving image a URI
//     * @param - fileName: String, mimeType: String
//     * @return - URI, values
//     * @author - Hongsi-Taste
//     */
//    private fun createImageUri(filename: String, mimeType: String): Uri? {
//        var values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
//        return this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//    }
//
//    /**
//     * @description - giving image a file name
//     * @param - None
//     * @return - fileName: String
//     * @author - Hongsi-Taste
//     */
//    private fun newFileName(): String {
//        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
//        val filename = sdf.format(System.currentTimeMillis())
//        return "$filename.jpg"
//    }

}