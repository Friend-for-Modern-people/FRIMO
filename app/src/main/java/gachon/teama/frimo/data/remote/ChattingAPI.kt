package gachon.teama.frimo.data.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChattingAPI {

    // 단어 추가하는 API
//    @POST("chatting/{userPk}")
//    fun chat(@Body word: Chatting): Call<String>

    @GET("chatbot/{message}")
    suspend fun getMessage(@Path("message") message: String) : Response<String>
}