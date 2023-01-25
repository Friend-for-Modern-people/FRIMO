package gachon.teama.frimo.data.remote

import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.data.entities.DiaryDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DiaryAPI {

    // 유저가 작성한 일기를 최신순으로 가져오는 API
    @GET("diary/{userPK}")
    suspend fun getDiary(@Path("userPK") userId: Long) : List<Diary>

    // 유저가 작성한 일기의 개수를 가져오는 API
    @GET("diary/{userPK}/cnt")
    suspend fun getDiaryCount(@Path("userPK") userId: Long) : Int

    // 유저가 특정 연도에 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/{year}")
    fun getDiaryByYear(@Path("userPK") userId: Long, @Path("year") year: Int) : Call<List<Diary>>

    // 유저가 특정 달에 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/{year}/{month}")
    fun getDiaryByMonth(@Path("userPK") userId: Long, @Path("year") year: Int, @Path("month") month: Int) : Call<List<Diary>>

    // 유저가 특정 감정으로 작성한 diary를 가져오는 API
    @GET("diary/{userPK}/mainSent/{sent}")
    fun getDiaryBySentiment(@Path("userPK") userId: Long, @Path("sent") sentiment: Int) : Call<List<Diary>> // sent :0~5

    // diary id로 해당 diary 가져오는 API
    @GET("diary/{diaryPK}/only1")
    suspend fun getDiaryById(@Path("diaryPK") diaryId: Long) : Diary

    // 일기 하나 가져오는 API
    @GET("diary/{diaryPK}/only1")
    fun getOneDiary(@Path("diaryPK") diaryId: Long) : Call<DiaryDto>

}

/*

    /**
     * @description - 최신순의 일기 가져오기
     * @Param @Path {userpk}
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiaries(@Path("userPK") Long userPK);

    /**
     * @description - 작성된 일기의 개수를 가져오는 API
     * @Param @Path Long {userpk}
     * @return - Integer
     * @author - vivi108
     */
    @GET("diary/{userPK}/cnt")
    Call<Integer> getDiariesCnt(@Path("userPK") Long userPK);

    /**
     * @description - 월별 일기를 가져오는 API
     * @Param @Path Long {userpk} , @Path int {month}
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}/{month}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiariesbyMonth(@Path("userPK") Long userPK, @Path("month") int month);

    /**
     * @description - 년도별 일기를 가져오는 API
     * @Param @Path Long {userpk} , @Path int {year}, @Path int {month}
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}/{year}/{month}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiariesbyYear(@Path("userPK") Long userPK, @Path("year") int year, @Path("month") int month);

    /**
     * @description - 감정별 일기를 가져오는 API
     * @Param @Path Long {userpk} , @Path int {sent} //0~5
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}/mainSent/{sent}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiariesbySent(@Path("userPK") Long userPK, @Path("sent") int sent);

 */