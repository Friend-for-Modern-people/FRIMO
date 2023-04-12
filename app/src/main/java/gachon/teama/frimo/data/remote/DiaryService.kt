package gachon.teama.frimo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DiaryService {

    // 유저가 작성한 일기를 최신순으로 가져오는 API
    @GET("diary/{userPK}")
    suspend fun getDiary(@Path("userPK") userId: Long) : Response<List<Diary>>

    // 유저가 작성한 일기의 개수를 가져오는 API
    @GET("diary/{userPK}/cnt")
    suspend fun getDiaryCount(@Path("userPK") userId: Long) : Response<Int>

    // 유저가 특정 연도에 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/{year}")
    suspend fun getDiaryByYear(@Path("userPK") userId: Long, @Path("year") year: Int) : Response<List<Diary>>

    // 유저가 특정 달에 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/{year}/{month}")
    suspend fun getDiaryByMonth(@Path("userPK") userId: Long, @Path("year") year: Int, @Path("month") month: Int) : Response<List<Diary>>

    // 유저가 특정 감정으로 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/mainSent/{sent}")
    suspend fun getDiaryBySentiment(@Path("userPK") userId: Long, @Path("sent") sentiment: Int) : Response<List<Diary>> // sent :0~5

    // diary id로 해당 diary 가져오는 API
    @GET("diary/{diaryPK}/only1")
    suspend fun getDiaryById(@Path("diaryPK") diaryId: Long) : Response<Diary>

}