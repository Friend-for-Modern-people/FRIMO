package gachon.teama.frimo.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import gachon.teama.frimo.data.remote.DiaryKeywordsAPI.AddWordRequest

object Server {

    private const val URL = "http://218.48.213.10:80/app/"
    private val diaryAPI: DiaryAPI
    private val diaryKeywordsAPI: DiaryKeywordsAPI

    init {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        diaryAPI = retrofit.create(DiaryAPI::class.java)
        diaryKeywordsAPI = retrofit.create(DiaryKeywordsAPI::class.java)
    }

    // ---------- Diary api ----------

    // 유저가 작성한 일기를 최신순으로 가져옴
    suspend fun getDiary(userId: Long): List<Diary> = withContext(Dispatchers.IO) {
        val response = diaryAPI.getDiary(userId)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 유저가 작성한 일기의 개수를 가져오는 API
    suspend fun getDiaryCount(userId: Long): Int = withContext(Dispatchers.IO) {
        val response = diaryAPI.getDiaryCount(userId)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }


    // Diary id로 해당 diary를 가져옴
    suspend fun getDiaryById(diaryId: Long): Diary = withContext(Dispatchers.IO) {
        val response = diaryAPI.getDiaryById(diaryId)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // ---------- Diary interest api ----------

    // 사용자가 작성한 단어를 모두 받아옴
    suspend fun getWord(diaryId: Long): List<DiaryKeywords> = withContext(Dispatchers.IO) {
        val response = diaryKeywordsAPI.getWord(diaryId)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 사용자가 작성한 대표 단어 4개만 받아옴
    suspend fun getFourWord(diaryId: Long): List<DiaryKeywords> = withContext(Dispatchers.IO) {
        val response = diaryKeywordsAPI.getFourWord(diaryId)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 사용자가 추가하고 싶은 단어를 추가함
    suspend fun addWord(request: AddWordRequest): Boolean = withContext(Dispatchers.IO) {
        val response = diaryKeywordsAPI.addWord(request)
        return@withContext response.isSuccessful && response.code() == 201
    }

}