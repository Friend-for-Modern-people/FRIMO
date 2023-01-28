package gachon.teama.frimo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Friend::class, User::class], version = 1)

/* 추상 클래스 */
abstract class AppDatabase: RoomDatabase() {
    abstract fun friendDao(): FriendDAO
    abstract fun userDao(): UserDAO

    companion object { // 동반 객체 (singleton)
        private var instance: AppDatabase? = null // 한 번만 생성해서 모든 객체가 공유해서 사용

        @Synchronized // 실행순서가 없었던 스레드들의 순서를 만들어주는 것 (동기화)
        fun getInstance(context: Context): AppDatabase? {
            if(instance == null) { // instance가 없다면 instance의 RoomDB를 만들어줌
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder (
                        context.applicationContext,
                        AppDatabase::class.java,
                        "user-database" // 다른 데이터베이스랑 이름 겹치면 꼬이게 된다.
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}