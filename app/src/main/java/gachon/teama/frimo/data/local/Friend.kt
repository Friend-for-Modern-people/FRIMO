package gachon.teama.frimo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Friend (
    var name: String, // Character name
    var img_profile: Int, // Character image
    var like: Boolean, // 캐릭터 하트 여부
    var warmth: Int, // 캐릭터 따뜻함지수
    var sympathy : Int, // 캐릭터 공감지수
    var introduce : String, // Introduce character
    var live : String, // Where character lives
    var img_live : Int, // (Image) Where character lives
    var height : String, // 캐릭터 키
    var img_height : Int, // (Image) 캐릭터 키
    var prefer : String, // What character prefers
    var img_prefer : Int, // (Image) What character prefers
    var img_recommendation : Int, // 오늘의 추천 캐릭터에서 보여줄 이미지
    var img_theme : Int, // 캐릭터 테마에서 보여줄 이미지
    var tag: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}