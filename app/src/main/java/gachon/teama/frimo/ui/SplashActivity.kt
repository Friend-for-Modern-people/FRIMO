package gachon.teama.frimo.ui

import android.os.Handler
import android.os.Looper
import android.util.Log
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.Friend
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    // Database
    private val database by lazy { AppDatabase.getInstance(this@SplashActivity)!! }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setFriend()

        // Todo: (Not now) 카카오 로그인 구현 시 토큰 리프레쉬? 하는 코드 작성
        // Delay screen
        Handler(Looper.getMainLooper()).postDelayed({

            // If the user has set a nickname, go to the main page, otherwise go to the login page
            if (database.userDao().getNickname() != null) {
                startNextActivity(MainActivity::class.java)
            } else {
                startNextActivity(OnboardingActivity::class.java)
            }

        }, 3000)

    }

    /**
     * @description - 처음 앱을 설치하여 data가 없는 경우, 친구 정보 추가
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setFriend(){

        val friendList = database.friendDao().getFriendList() as ArrayList

        // Add data if there is no character information in roomDB
        if (friendList.isEmpty()) {
            database.friendDao().insert(
                Friend(
                    name = "따뜻한 그루터기씨",
                    img_profile = R.drawable.img_profile_geuluteogi,
                    like = false,
                    warmth = 77,
                    sympathy = 23,
                    introduce = "언제나 따뜻한 화분속에 있는 것을 좋아하는\n" +
                            "그루터기씨. 따뜻한 화분 만큼 마음도 따뜻하다.\n" +
                            "다른 사람의 말을 들어주기를 좋아함.",
                    live = "깨끗한 호수",
                    img_live = R.drawable.img_live_geuluteogi,
                    height = "10cm (새싹 포함)",
                    img_height = R.drawable.img_height_geuluteogi,
                    prefer = "아이스 아메리카노",
                    img_prefer = R.drawable.img_prefer_geuluteogi,
                    img_recommendation = R.drawable.img_recommendation_geuluteogi,
                    img_theme = R.drawable.img_theme_geuluteogi,
                    tag = "#따뜻함 #포근"
                )
            )

            database.friendDao().insert(
                Friend(
                    name = "복덕방 할매",
                    img_profile = R.drawable.img_profile_halmae,
                    like = false,
                    warmth = 92,
                    sympathy = 8,
                    introduce = "나이는 항상 비밀이다\n" +
                            "하지만 언제나 밝은 미소로 반겨주는 따뜻한 할머니.\n" +
                            "20대때 잘 나갔다고는 하지만 확인되지는 않는다.",
                    live = "해바라기 밭",
                    img_live = R.drawable.img_live_halmae,
                    height = "15cm (20대 때)",
                    img_height = R.drawable.img_height_halmae,
                    prefer = "따뜻한 온돌",
                    img_prefer = R.drawable.img_prefer_halmae,
                    img_recommendation = R.drawable.img_recommendation_halmae,
                    img_theme = R.drawable.img_theme_halmae,
                    tag = "#따뜻함 #친숙함"
                )
            )

            database.friendDao().insert(
                Friend(
                    name = "갓선비",
                    img_profile = R.drawable.img_profile_seonbi,
                    like = false,
                    warmth = 70,
                    sympathy = 30,
                    introduce = "언제나 따뜻한 화분속에 있는 것을 좋아하는\n" +
                            "그루터기씨. 따뜻한 화분 만큼 마음도 따뜻하다.\n" +
                            "다른 사람의 말을 들어주기를 좋아함.",
                    live = "초가집",
                    img_live = R.drawable.img_live_seonbi,
                    height = "10cm (갓 제외)",
                    img_height = R.drawable.img_height_seonbi,
                    prefer = "엿",
                    img_prefer = R.drawable.img_prefer_seonbi,
                    img_recommendation = R.drawable.img_recommendation_seonbi,
                    img_theme = R.drawable.img_theme_seonbi,
                    tag = "#친숙함 #존경"
                )
            )

            database.friendDao().insert(
                Friend(
                    name = "우울한가, 휴먼?",
                    img_profile = R.drawable.img_profile_human,
                    like = false,
                    warmth = 77,
                    sympathy = 23,
                    introduce = "단 한번도 얼굴을 보여준 적이 없다. 나이도 미지수.\n" +
                            "공감능력은 많이 떨어지지만 따뜻함만큼은 높다.\n" +
                            "앞이 잘 보이지 않아서(?) 가끔 넘어진다.",
                    live = "우주",
                    img_live = R.drawable.img_live_human,
                    height = "기분에 따라 다름",
                    img_height = R.drawable.img_height_human,
                    prefer = "전기 충전기",
                    img_prefer = R.drawable.img_prefer_human,
                    img_recommendation = R.drawable.img_recommendation_human,
                    img_theme = R.drawable.img_theme_human,
                    tag = "#차분 #따뜻함"
                )
            )

            Log.d("roomdb", "insert human success")

            database.friendDao().insert(
                Friend(
                    name = "나는야 젠틀맨",
                    img_profile = R.drawable.img_profile_gentleman,
                    like = false,
                    warmth = 80,
                    sympathy = 20,
                    introduce = "멋있는 수염을 기르고 있다. 하지만 본인 수염인지 가짜인지...\n" +
                            "날마다 턱시도 양복을 입고 다닌다.\n" +
                            "양복이 365벌 있다는 소문이 있다.",
                    live = "비가 오는 곳",
                    img_live = R.drawable.img_live_gentleman,
                    height = "18cm (모자포함)",
                    img_height = R.drawable.img_height_gentleman,
                    prefer = "나비 넥타이",
                    img_prefer = R.drawable.img_prefer_gentleman,
                    img_recommendation = R.drawable.img_recommendation_gentleman,
                    img_theme = R.drawable.img_theme_gentleman,
                    tag = "#차분 #따뜻함 #존경"
                )
            )

            database.friendDao().insert(
                Friend(
                    name = "ENFP",
                    img_profile = R.drawable.img_profile_enfp,
                    like = false,
                    warmth = 40,
                    sympathy = 60,
                    introduce = "머리 위의 새싹을 번개모양으로 바꿨다.\n" +
                            "덕분에 부모님에게 엄청 혼남.\n" +
                            "일렉트로닉 기타를 치기를 좋아하며 자유로운 영혼이다.",
                    live = "선인장",
                    img_live = R.drawable.img_live_enfp,
                    height = "15cm (20대 때)",
                    img_height = R.drawable.img_height_enfp,
                    prefer = "일렉트로닉 기타",
                    img_prefer = R.drawable.img_prefer_enfp,
                    img_recommendation = R.drawable.img_recommendation_enfp,
                    img_theme = R.drawable.img_theme_enfp,
                    tag = "#친숙함"
                )
            )

            database.friendDao().insert(
                Friend(
                    name = "칭구",
                    img_profile = R.drawable.img_profile_chinggu,
                    like = false,
                    warmth = 50,
                    sympathy = 50,
                    introduce = "활력있는 스포츠를 좋아하는 가장 친한 칭구.\n" +
                            "열정있는 마음과 달리 몸이 따라주지 않는다. 선천적 몸치.\n" +
                            "특정 스포츠 브랜드의 모자만을 고집해서 쓰고 다닌다.",
                    live = "테니스 코트",
                    img_live = R.drawable.img_live_chinggu,
                    height = "아주 큼 (근거없음)",
                    img_height = R.drawable.img_height_chinggu,
                    prefer = "테니스",
                    img_prefer = R.drawable.img_prefer_chinggu,
                    img_recommendation = R.drawable.img_recommendation_chinggu,
                    img_theme = R.drawable.img_theme_chinggu,
                    tag = "#따뜻함 #친숙함"
                )
            )

        }

    }
}