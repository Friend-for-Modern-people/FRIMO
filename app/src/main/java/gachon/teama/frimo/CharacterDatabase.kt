package gachon.teama.frimo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Character::class], version = 1)

/* 추상 클래스 */
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDAO // 실제로 구현을 해줘야 하는 부분

    companion object { // 동반 객체 (singleton)
        private var instance: CharacterDatabase? = null // 한 번만 생성해서 모든 객체가 공유해서 사용

        @Synchronized // 동기화: 실행순서가 없었던 스레드들의 순서를 만들어주는 것
        fun getInstance(context: Context): CharacterDatabase? {
            if(instance == null) { // instance가 없다면 instance의 RoomDB를 만들어준다고 이해하면 된다.
                synchronized(CharacterDatabase::class) {
                    instance = Room.databaseBuilder (
                        context.applicationContext,
                        CharacterDatabase::class.java,
                        "user-database" // 다른 데이터베이스랑 이름 겹치면 꼬이게 된다.
                    ).build()
                }
            }
            return instance
        }
    }
}