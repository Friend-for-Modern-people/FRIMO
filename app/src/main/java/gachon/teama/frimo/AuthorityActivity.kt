package gachon.teama.frimo


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import gachon.teama.frimo.databinding.ActivityAuthorityBinding

class AuthorityActivity : ComponentActivity() {

    private lateinit var binding: ActivityAuthorityBinding

    // 퍼미션 응답 처리 코드
    private val multiplePermissionsCode = 100

    // 필요한 퍼미션 리스트
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityAuthorityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            checkboxAll.setOnClickListener {

                // Activate next button when full permission is received
                buttonNext.isEnabled = checkboxAll.isChecked

                // Adjust the child check box with the parent check box
                if (checkboxAll.isChecked) {

                    checkboxFile.isChecked = true
                    checkboxInternet.isChecked = true
                    checkboxCamera.isChecked = true
                    checkboxMic.isChecked = true
                } else {

                    checkboxFile.isChecked = false
                    checkboxInternet.isChecked = false
                    checkboxCamera.isChecked = false
                    checkboxMic.isChecked = false
                }

            }

            checkboxFile.setOnClickListener {
                updateCheckbox()
            }

            checkboxInternet.setOnClickListener {
                updateCheckbox()
            }

            checkboxCamera.setOnClickListener {
                updateCheckbox()
            }

            checkboxMic.setOnClickListener {
                updateCheckbox()
            }

        }

        // When start button clicked
        binding.buttonNext.setOnClickListener {
//            checkPermissionsAndRun()
            startActivity(Intent(this, SetNicknameActivity::class.java))
        }

    }

    fun updateCheckbox(){

        with(binding){

            // If any of the lower checkboxes are not clicked, unclick the upper checkbox.
            // When all child checkboxes are clicked, the parent checkbox is clicked
            checkboxAll.isChecked = checkboxFile.isChecked && checkboxInternet.isChecked && checkboxCamera.isChecked && checkboxMic.isChecked

            // Activate next button when full permission is received
            buttonNext.isEnabled = checkboxAll.isChecked
        }
    }
//
//    // 퍼미션 체크 및 권한 요청 함수
//    private fun checkPermissionsAndRun() {
//
//        // 거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
//        val rejectedPermissionList = ArrayList<String>()
//
//        // 필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
//        for(permission in requiredPermissions){
//            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//
//                // 만약 권한이 없다면 rejectedPermissionList에 추가
//                rejectedPermissionList.add(permission)
//            }
//        }
//
//        // 거절된 퍼미션이 있다면...
//        if(rejectedPermissionList.isNotEmpty()){
//
//            // 권한 요청!
//            val array = arrayOfNulls<String>(rejectedPermissionList.size)
//            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
//        }
//    }
//
//    // 권한 요청 결과 함수
//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        // Todo: 권한을 전부 못 받은 경우, 권한을 전부 허락해줄 때까지 요청 보내기
//        when (requestCode) {
//            multiplePermissionsCode -> {
//                if(grantResults.isNotEmpty()) {
//                    for((i, permission) in permissions.withIndex()) {
//                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                            // 권한 획득 실패
//                            Log.i("TAG", "The user has denied to $permission")
//                        }
//                    }
//                }
//            }
//        }
//
//        startActivity(Intent(this, SetNicknameActivity::class.java))
//
//    }
}