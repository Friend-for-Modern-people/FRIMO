package gachon.teama.frimo.data.remote

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiaryInterestAPI {

    // 사용자가 작성한 단어를 모두 받아오는 API
    @GET("tag/{diaryPK}")
    suspend fun getWord(@Path("diaryPK") diaryId: Long): Response<List<Words>>

    // 사용자가 작성한 대표 단어 4개만 받아오는 API
    @GET("tag/{diaryPK}/only4")
    suspend fun getFourWord(@Path("diaryPK") diaryId: Long): Response<List<Words>>

    // 단어 추가하는 API
    @POST("tag")
    suspend fun addWord(@Body word: AddWordRequest): Response<ResponseBody>

    data class AddWordRequest(
        @SerializedName("diaryPk") val id: Long,
        @SerializedName("tagContent") val content: String,
        @SerializedName("sentPK") val sentiment: Long,
        @SerializedName("category") val category: String
    )

    data class Words(
        @SerializedName("diaryPk") val diaryId: Long, // 다이어리 id
        @SerializedName("tagContent") val word: String, // 내가 쓴 단어들
        @SerializedName("sentLargeId") val sentiment: Int, // 감정 대분류
        @SerializedName("sentPK") val sentimentDetail: Int, // 감정 소분류
        @SerializedName("category") val category: String // 카테고리
    )
}