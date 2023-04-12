package gachon.teama.frimo.data.remote

import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import gachon.teama.frimo.data.remote.DiaryKeywordsService.AddWordRequest

object DiaryServer {

    private const val URL = "http://218.48.213.121:80/app/"

    private val client = OkHttpClient.Builder().build()
    private val gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    private val diaryService: DiaryService = retrofit.create(DiaryService::class.java)
    private val diaryKeywordsService: DiaryKeywordsService = retrofit.create(DiaryKeywordsService::class.java)

    // ---------- Diary API ----------

    // 유저가 작성한 일기를 최신순으로 가져옴
    suspend fun getDiary(userId: Long): List<Diary> = withContext(Dispatchers.IO) {
        diaryService.getDiary(userId).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 유저가 작성한 일기의 개수를 가져오는 API
    suspend fun getDiaryCount(userId: Long): Int = withContext(Dispatchers.IO) {
        diaryService.getDiaryCount(userId).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 유저가 특정 연도에 작성한 diary를 가져오는 API
    suspend fun getDiaryByYear(userId: Long, year: Int): List<Diary> = withContext(Dispatchers.IO) {
        diaryService.getDiaryByYear(userId, year).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 유저가 특정 달에 작성한 diary를 가져오는 API
    suspend fun getDiaryByMonth(userId: Long, year: Int, month: Int): List<Diary> =
        withContext(Dispatchers.IO) {
            diaryService.getDiaryByMonth(userId, year, month).let { response ->
                if (response.isSuccessful) response.body()!!
                else throw Exception(response.errorBody()?.charStream()?.readText())
            }
        }


    // 유저가 특정 감정으로 작성한 diary를 가져오는 API
    suspend fun getDiaryBySentiment(userId: Long, sentiment: Int): List<Diary> = withContext(Dispatchers.IO) {
        diaryService.getDiaryBySentiment(userId, sentiment).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // Diary id로 해당 diary를 가져옴
    suspend fun getDiaryById(diaryId: Long): Diary = withContext(Dispatchers.IO) {
        diaryService.getDiaryById(diaryId).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // ---------- Diary Interest API ----------

    // 사용자가 작성한 단어를 모두 받아옴
    suspend fun getWord(diaryId: Long): List<DiaryKeywords> = withContext(Dispatchers.IO) {
        diaryKeywordsService.getWord(diaryId).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 사용자가 작성한 대표 단어 4개만 받아옴
    suspend fun getFourWord(diaryId: Long): List<DiaryKeywords> = withContext(Dispatchers.IO) {
        diaryKeywordsService.getFourWord(diaryId).let { response ->
            if (response.isSuccessful) response.body()!!
            else throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }

    // 사용자가 추가하고 싶은 단어를 추가함
    suspend fun addWord(request: AddWordRequest): Boolean = withContext(Dispatchers.IO) {
        diaryKeywordsService.addWord(request).run {
            isSuccessful && code() == 201
        }
    }

}