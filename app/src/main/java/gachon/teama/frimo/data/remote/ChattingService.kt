package gachon.teama.frimo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChattingService {

    // 유저가 chatbot에게 message를 보내면 그에 대한 응답을 받아오는 API
    @GET("chatbot/{message}")
    suspend fun getMessage(@Path("message") message: String) : Response<String>
}