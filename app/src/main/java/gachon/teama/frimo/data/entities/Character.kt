package gachon.teama.frimo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character (
    var name: String, // Character name
    var image: Int, // Character image
    var like: Boolean, // 캐릭터 하트 여부
    var introduce : String, // Introduce character
    var live : String, // Where character lives
    var img_live : Int, // (Image) Where character lives
    var height : String, // 캐릭터 키
    var prefer : String, // What character prefers
    var img_prefer : Int, // (Image) What character prefers
    var img_recommendation : Int, // 오늘의 추천 캐릭터에서 보여줄 이미지
    var img_theme : Int // 캐릭터 테마에서 보여줄 이미지
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 1
}