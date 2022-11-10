package gachon.teama.frimo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character (
    var name: String, // 이름
    var image: Int, // 캐릭터 이미지
    var like: Boolean, // 캐릭터 하트 여부
    var introduce : String, // 캐릭터 소개
    var live : String, // 캐릭터 사는 곳
    var height : String, // 캐릭터 키
    var preference_image : Int, // 캐릭터가 선호하는 것 (이미지)
    var preference : String // 캐릭터가 선호하는 것 (String)
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}