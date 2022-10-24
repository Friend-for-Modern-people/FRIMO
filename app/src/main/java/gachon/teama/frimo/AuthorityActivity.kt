package gachon.teama.frimo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import gachon.teama.frimo.databinding.ActivityAuthorityBinding

class AuthorityActivity : ComponentActivity() {

    private lateinit var binding: ActivityAuthorityBinding

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
                    textviewFile.text ="파일 접근 권한이 수락되었습니다"

                    checkboxInternet.isChecked = true
                    textviewInternet.text ="인터넷 접근 권한이 수락되었습니다"

                    checkboxCamera.isChecked = true
                    textviewCamera.text ="카메라 접근 권한이 수락되었습니다"

                    checkboxMic.isChecked = true
                    textviewMic.text ="마이크 접근 권한이 수락되었습니다"

                } else {

                    checkboxFile.isChecked = false
                    textviewFile.text ="파일 접근 권한이 필요합니다"

                    checkboxInternet.isChecked = false
                    textviewInternet.text ="인터넷 접근 권한이 필요합니다"

                    checkboxCamera.isChecked = false
                    textviewCamera.text ="카메라 접근 권한이 필요합니다"

                    checkboxMic.isChecked = false
                    textviewMic.text ="마이크 접근 권한이 필요합니다"

                }

            }

            checkboxFile.setOnClickListener {

                updateCheckbox()

                // Indicate whether or not the permission is accepted by clicking the checkbox
                if(checkboxFile.isChecked)
                    textviewFile.text ="파일 접근 권한이 수락되었습니다"
                else
                    textviewFile.text ="파일 접근 권한이 필요합니다"
            }

            checkboxInternet.setOnClickListener {

                updateCheckbox()

                // Indicate whether or not the permission is accepted by clicking the checkbox
                if(checkboxInternet.isChecked)
                    textviewInternet.text ="인터넷 접근 권한이 수락되었습니다"
                else
                    textviewInternet.text ="인터넷 접근 권한이 필요합니다"
            }

            checkboxCamera.setOnClickListener {

                updateCheckbox()

                // Indicate whether or not the permission is accepted by clicking the checkbox
                if(checkboxCamera.isChecked)
                    textviewCamera.text ="카메라 접근 권한이 수락되었습니다"
                else
                    textviewCamera.text ="카메라 접근 권한이 필요합니다"
            }

            checkboxMic.setOnClickListener {

                updateCheckbox()

                // Indicate whether or not the permission is accepted by clicking the checkbox
                if(checkboxMic.isChecked)
                    textviewMic.text ="마이크 접근 권한이 수락되었습니다"
                else
                    textviewMic.text ="마이크 접근 권한이 필요합니다"
            }

        }

        // When start button clicked
        binding.buttonNext.setOnClickListener {

            // Todo: 권한 받아오기



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
}