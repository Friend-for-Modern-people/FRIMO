package gachon.teama.frimo.ui


import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityAuthorityBinding

class AuthorityActivity : BaseActivity<ActivityAuthorityBinding>(ActivityAuthorityBinding::inflate) {

    // Permission
    private val multiplePermissionsCode = 100 // 퍼미션 응답 처리 코드
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) // 필요한 퍼미션 리스트

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setClickListener()
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() = with(binding) {

        // Set all checkbox click listener
        checkboxAll.setOnClickListener {
            if (checkboxAll.isChecked) {
                checkboxFile.isChecked = true
                checkboxInternet.isChecked = true
                checkboxCamera.isChecked = true
                checkboxMic.isChecked = true
                setScreen()

            } else {
                checkboxFile.isChecked = false
                checkboxInternet.isChecked = false
                checkboxCamera.isChecked = false
                checkboxMic.isChecked = false
                setScreen()
            }
        }

        // Set file checkbox click listener
        checkboxFile.setOnClickListener {
            setScreen()
        }

        // Set internet checkbox click listener
        checkboxInternet.setOnClickListener {
            setScreen()
        }

        // Set camera checkbox click listener
        checkboxCamera.setOnClickListener {
            setScreen()
        }

        // Set mic checkbox click listener
        checkboxMic.setOnClickListener {
            setScreen()
        }

        // Set start button click listener
        buttonNext.setOnClickListener {
            checkPermissionsAndRun()
        }
    }

    /**
     * @description - 유저가 선택한 체크박스에 따라 화면 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() = with(binding) {

        // 하위 체크박스 선택 여부에 따라 전체 동의 체크 박스 선택 변경
        checkboxAll.isChecked = checkboxFile.isChecked && checkboxInternet.isChecked && checkboxCamera.isChecked && checkboxMic.isChecked

        // 모든 권한을 주겠다고 유저에게 허락이 떨어지면 다음 화면으로 넘어가는 버튼 활성화
        buttonNext.isEnabled = checkboxAll.isChecked

        // File 체크버튼 클릭 여부 확인
        if (checkboxFile.isChecked) {
            textviewTextGiveAuthority1.visibility = View.GONE
        } else {
            textviewTextGiveAuthority1.visibility = View.VISIBLE
        }

        // Internet 체크버튼 클릭 여부 확인
        if (checkboxInternet.isChecked) {
            textviewTextGiveAuthority2.visibility = View.GONE
        } else {
            textviewTextGiveAuthority2.visibility = View.VISIBLE
        }

        // Mic 체크버튼 클릭 여부 확인
        if (checkboxMic.isChecked) {
            textviewTextGiveAuthority4.visibility = View.GONE
        } else {
            textviewTextGiveAuthority4.visibility = View.VISIBLE
        }

        // Camera 체크버튼 클릭 여부 확인
        if (checkboxCamera.isChecked) {
            textviewTextGiveAuthority3.visibility = View.GONE
        } else {
            textviewTextGiveAuthority3.visibility = View.VISIBLE
        }
    }

    /**
     * @description - 퍼미션 체크 및 권한 요청
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun checkPermissionsAndRun() {

        // 거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        val rejectedPermissionList = ArrayList<String>()

        // 필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                // 만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }

        // 거절된 퍼미션이 있는 경우
        if (rejectedPermissionList.isNotEmpty()) {

            // 권한 요청
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }

    /**
     * @description - 권한 요청 결과 함수
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            multiplePermissionsCode -> {
                if (grantResults.isNotEmpty()) {
                    for ((i, permission) in permissions.withIndex()) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            // 권한 획득 실패
                            Log.i("TAG", "The user has denied to $permission")
                        }
                    }
                }
            }
        }

        startNextActivity(SetNicknameActivity::class.java)
    }
}