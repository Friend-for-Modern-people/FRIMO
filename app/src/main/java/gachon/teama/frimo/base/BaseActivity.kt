package gachon.teama.frimo.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

// AppCompatActivity를 상속받아 Activity 역할을 하는 가장 기본적인 Activity
// 자동적으로 뷰 바인딩을 해주기 때문에 BaseActivity를 상속받아 필요한 것들만 넘겨주면 자동적으로 onCreate() 수행되면서 뷰 바인딩이 된다.
abstract class BaseActivity<T: ViewBinding>(private val inflate: (LayoutInflater) -> T): AppCompatActivity(){
    protected lateinit var binding: T
        private set

    private var imm : InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        initAfterBinding()
    }

    // 뷰 바인딩이 끝나고 호출되는 추상 메소드
    // BaseActivity를 상속받은 activity는 이 메소드를 정의해줌으로써 자동적으로 실행되게 해준다.
    protected abstract fun initAfterBinding()

    // Toast message를 message만 넣어서 불러오도록
    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // 이전 Activity는 남기고, 다음 activity를 켜주는 역할
    fun startNextActivity(activity: Class<*>?) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    // 이전 Activity를 모두 날려주고, 다음 activity를 켜주는 역할
    fun startActivityWithClear(activity: Class<*>?) {
        val intent = Intent(this, activity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    // 키보드 숨기기
    // 디바이스에서 키보드가 뜨는 것을 자동적으로 숨겨주는 역할
    fun hideKeyboard(v: View){
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}