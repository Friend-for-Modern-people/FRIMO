package gachon.teama.frimo.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DiaryAPI {

    @GET("diary/{userPK}/cnt")
    fun getDiaryCount(@Path("userPK") userId: Long) : Call<Int>

}