package gachon.teama.frimo.data.remote;


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
    @GET("user/NNcheck/{userNN}")
    Call<Boolean> checkUserNNDuplicate(@Path("userNN") Long userNN);

    /**
     * @description - 회원탈퇴
     * @Param @Path {userpk}
     * @return - 200 OK, "# is deleted"
     * @author - vivi108
     */
    @DELETE("user/{userPK}")
    Call<String> deleteUser(@Path("userPK") Long userPK);

    /**
     * @description - 회원조회
     * @Param @Path {userpk}
     * @Param @return - 200 OK, "# is deleted"
     * @author - vivi108
     */
    @GET("user/{userPK}")
    Call<UserDto.GetUserOnlyInfoResponseDto> getUserInfo(@Path("userPK") Long userPK);

    /**
     * @description - 닉네임으로 pk 가져오기
     * @Param @Path {userNN}
     * @Param @return - Long userPK
     * @author - vivi108
     */
    @GET("user/userinfo/{userNN}")
    Call<UserDto.GetUserOnlyInfoResponseDto> getUserPk(@Path("userNN") String userNN);

}
