package gachon.teama.frimo.retrofit;

import com.google.firebase.database.core.Repo;

import java.util.List;

import gachon.teama.frimo.data.entities.User;
import gachon.teama.frimo.retrofit.dto.DiaryDto;
import gachon.teama.frimo.retrofit.dto.DiaryInterestTagDto;
import gachon.teama.frimo.retrofit.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    /**
     * @description - 회원가입
     * @RequestBody UserDto.registRequestDto
     * @return - 201 CREATED , saved
     * @author - vivi108
     */
    @POST("user")
    Call<String> RegistUser(@Body UserDto.RegistRequestDto user );

    /**
     * @description - 닉네임 중복확인
     * @Param @Path {userNickName}
     * @return - 200 OK
     * @author - vivi108
     */
    @GET("user/{userNN}")
    Call<Boolean> checkUserNNDuplicate(@Path("userNN") String userNN);

    /**
     * @description - 회원탈퇴
     * @Param @Path {userpk}
     * @return - 200 OK, "# is deleted"
     * @author - vivi108
     */
    @DELETE("user/{userPK}")
    Call<String> deleteUser(@Path("userPK") String userPK);

    /**
     * @description - 회원조회
     * @Param @Path {userpk}
     * @Param @return - 200 OK, "# is deleted"
     * @author - vivi108
     */
    @GET("user/{userPK}")
    Call<UserDto.GetUserOnlyInfoResponseDto> getUserInfo(@Path("userPK") String userPK);

    // **DIARY** TABLE BELOW ----------------------------------------------------------

    /**
     * @description - 최신순의 일기 가져오기
     * @Param @Path {userpk}
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiaries(@Path("userPK") String userPK);

    /**
     * @description - 월별 일기를 가져오는 API
     * @Param @Path Long {userpk} , @Path int {month}
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}/{month}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiariesbyMonth(@Path("userPK") String userPK, @Path("month") int month);

    /**
     * @description - 년도별 일기를 가져오는 API
     * @Param @Path Long {userpk} , @Path int {year}
     * @return - List<DiaryDto.GetDiaryResponseDto>
     * @author - vivi108
     */
    @GET("diary/{userPK}/{year}")
    Call<List<DiaryDto.GetDiaryResponseDto>> getDiariesbyYear(@Path("userPK") String userPK, @Path("year") int year);

    /**
     * @description - 작성된 일기의 개수를 가져오는 API
     * @Param @Path Long {userpk}
     * @return - Integer
     * @author - vivi108
     */
    @GET("diary/{userPK}/cnt")
    Call<Integer> getDiariesCnt(@Path("userPK") String userPK);

    // **DIARY INTEREST TAG** TABLE BELOW ----------------------------------------------------------
    /**
     * @description - 추가하는 태그를 저장하는 API
     * @Param @Path Long userPk, Long diaryPk @Body DiaryDto.AddDiaryRequestDto
     * @return - 201 CREATED , saved
     * @author - vivi108
     */
    @POST("tag/{userPK}/{diaryPK}")
    Call<String> addTag(@Path("userPk") Long userPk,
    @Path("diaryPk") Long diaryPk,
    @Body DiaryInterestTagDto.AddTagRequestDto addTagRequestDto);

    /**
     * @description - 일기에 속한 모든 테그 조회하는 API
     * @Param @Path Long userPk, Long diaryPk
     * @return - List<DiaryInterestTagDto.GetTagResponseDto>
     * @author - vivi108
     */
    @GET("tag/{userPK}/{diaryPK}")
    Call<List<DiaryInterestTagDto.GetTagResponseDto>> getTags(@Path("userPk") Long userPk,
                        @Path("diaryPk") Long diaryPk);

    /**
     * @description - 일기에 속한 4개 테그 조회하는 API
     * @Param @Path Long userPk, Long diaryPk
     * @return - List<DiaryInterestTagDto.GetTagResponseDto>
     * @author - vivi108
     */
    @GET("tag/{userPK}/{diaryPK}")
    Call<List<DiaryInterestTagDto.GetTagResponseDto>> get4Tags(@Path("userPk") Long userPk,
                                                              @Path("diaryPk") Long diaryPk);


}
