package gachon.teama.frimo.data.remote

import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import gachon.teama.frimo.data.remote.DiaryKeywordsAPI.AddWordRequest

object Server {

    // Todo: 다이어리 서버 주소 바뀌었는지 확인
    private const val URL = "http://218.48.213.10:80/app/"
    private const val chatUrl = "http://server.vivi108.com/"
    private val chatApi: ChattingAPI
    private val diaryAPI: DiaryAPI
    private val diaryKeywordsAPI: DiaryKeywordsAPI

    init {
        val client = OkHttpClient.Builder().build()

        var gson= GsonBuilder().setLenient().create()

        val chatRetrofit = Retrofit.Builder()
            .baseUrl(chatUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        chatApi = chatRetrofit.create(ChattingAPI::class.java)
        diaryAPI = retrofit.create(DiaryAPI::class.java)
        diaryKeywordsAPI = retrofit.create(DiaryKeywordsAPI::class.java)
    }

    // ---------- Chat API ----------

    // 유저가 chatbot에게 message를 보내면 그에 대한 응답을 받아옴
    suspend fun getMessage(message: String): String = withContext(Dispatchers.IO) {
        val response = chatApi.getMessage(message)
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else {
            throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // ---------- Diary API ----------

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

    // 유저가 특정 연도에 작성한 diary를 가져오는 API
    suspend fun getDiaryByYear(userId: Long, year: Int): ArrayList<Diary> =
        withContext(Dispatchers.IO) {
            val response = diaryAPI.getDiaryByYear(userId, year)
            if (response.isSuccessful) {
                return@withContext response.body()!! as ArrayList
            } else {
                throw Exception(response.errorBody()?.charStream()?.readText())
            }
        }

    // 유저가 특정 달에 작성한 diary를 가져오는 API
    suspend fun getDiaryByMonth(userId: Long, year: Int, month: Int): ArrayList<Diary> =
        withContext(Dispatchers.IO) {
            val response = diaryAPI.getDiaryByMonth(userId, year, month)
            if (response.isSuccessful) {
                return@withContext response.body()!! as ArrayList
            } else {
                throw Exception(response.errorBody()?.charStream()?.readText())
            }
        }

    // 유저가 특정 감정으로 작성한 diary를 가져오는 API
    suspend fun getDiaryBySentiment(userId: Long, sentiment: Int): ArrayList<Diary> =
        withContext(Dispatchers.IO) {
            val response = diaryAPI.getDiaryBySentiment(userId, sentiment)
            if (response.isSuccessful) {
                return@withContext response.body()!! as ArrayList
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

    // ---------- Diary Interest API ----------

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