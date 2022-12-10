package gachon.teama.frimo.data.remote

import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.retrofit.dao.DiaryInterestTag
import gachon.teama.frimo.retrofit.dto.DiaryInterestTagDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryInterestAPI {

    // 사용자가 작성한 단어를 모두 받아오는 API
    @GET("tag/{userPK}/{diaryPK}")
    fun getWord(@Path("userPK") userId : Long, @Path("diaryPK") diaryId : Long) : Call<List<DiaryInterestTagDto.GetTagResponseDto>>

    // 단어 추가하는 API
    @POST("tag/{userPK}/{diaryPK}")
    fun addWord(@Path("userPK") userId: Long, @Path("diaryPK") diaryId: Long, @Body addTagRequestDto: DiaryInterestTagDto.AddTagRequestDto) : Call<String>

}

/*

    /**
     * @description - 추가하는 태그를 저장하는 API
     * @Param @Path Long userPk, Long diaryPk @Body DiaryDto.AddDiaryRequestDto
     * @return - 201 CREATED , saved
     * @author - vivi108
     */
    @POST("tag/{userPK}/{diaryPK}")
    Call<String> addTag(@Path("userPK") Long userPk,
    @Path("diaryPK") Long diaryPk,
    @Body DiaryInterestTagDto.AddTagRequestDto addTagRequestDto);

    /**
     * @description - 일기에 속한 모든 테그 조회하는 API
     * @Param @Path Long userPk, Long diaryPk
     * @return - List<DiaryInterestTagDto.GetTagResponseDto>
     * @author - vivi108
     */
    @GET("tag/{userPK}/{diaryPK}")
    Call<List<DiaryInterestTagDto.GetTagResponseDto>> getTags(@Path("userPK") Long userPk,
                        @Path("diaryPK") Long diaryPk);

    /**
     * @description - 일기에 속한 4개 테그 조회하는 API
     * @Param @Path Long userPk, Long diaryPk
     * @return - List<DiaryInterestTagDto.GetTagResponseDto>
     * @author - vivi108
     */
    @GET("tag/{userPK}/{diaryPK}")
    Call<List<DiaryInterestTagDto.GetTagResponseDto>> get4Tags(@Path("userPK") Long userPk,
                                                              @Path("diaryPK") Long diaryPk);

 */