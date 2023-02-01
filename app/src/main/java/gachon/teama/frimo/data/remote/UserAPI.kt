package gachon.teama.frimo.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    // 회원 가입
    @POST("user")
    fun registUser(@Body user: RegisterUserRequest): Call<String>

    // 닉네임 중복 확인
    @GET("user/NNcheck/{userNN}")
    fun checkDuplicateNickname(@Path("userNN") nickname: String): Call<Boolean>

    // 회원 탈퇴
    @DELETE("user/{userPK}")
    fun deleteUser(@Path("userPK") userId: Long): Call<String>

    // user 조회
    @GET("user/{userPK}")
    fun getUserInfo(@Path("userPK") userId: Long): Call<GetUserInfoResponse>

    // user id 가져오기
    @GET("user/userinfo/{userNN}")
    fun getUserId(@Path("userNN") nickname: String): Call<GetUserInfoResponse>

    data class RegisterUserRequest(
        @SerializedName("userId") val id: String,
        @SerializedName("userNN") val nickname: String
    )

    data class GetUserInfoResponse(
        @SerializedName("userPk") val pk: Long,
        @SerializedName("userId") val id: String,
        @SerializedName("userNN") val nickname: String
    )
}

/*
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

 */

/*
package gachon.teama.frimo.data.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegistRequestDto {
        private String userId;
        private String userNN;

        @Builder
        public RegistRequestDto(String userId, String userNN) {
            this.userId = userId;
            this.userNN = userNN;
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserOnlyInfoResponseDto { // DB에서 가져온 댓글을 DTO로 변환하기 위한 DTO
        private Long userPk;
        private String userId;
        private String userNN;
    }
}
 */