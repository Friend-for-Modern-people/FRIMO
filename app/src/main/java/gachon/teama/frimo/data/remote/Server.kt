package gachon.teama.frimo.data.remote

import android.util.Log
import gachon.teama.frimo.data.remote.DiaryInterestAPI.Words
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import gachon.teama.frimo.data.remote.DiaryInterestAPI.AddWordRequest

object Server {

    private val tag = Server::class.java.name

    private const val URL = "http://218.48.213.10:80/app/"
    private val diaryInterestAPI: DiaryInterestAPI

    init {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        diaryInterestAPI = retrofit.create(DiaryInterestAPI::class.java)
    }

    // ---------- Diary interest api ----------

    suspend fun getWord(request: Long): List<Words> = withContext(Dispatchers.IO) {

        Log.d(tag, "Thread is ${Thread.currentThread().name}")

        val response = diaryInterestAPI.getWord(request)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    suspend fun getFourWord(request: Long): List<Words> = withContext(Dispatchers.IO) {

        Log.d(tag, "Thread is ${Thread.currentThread().name}")

        val response = diaryInterestAPI.getFourWord(request)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    suspend fun addWord(request: AddWordRequest): Boolean = withContext(Dispatchers.IO) {

        Log.d(tag, "Thread is ${Thread.currentThread().name}")

        val response = diaryInterestAPI.addWord(request)
        return@withContext response.isSuccessful && response.code() == 201
    }

}