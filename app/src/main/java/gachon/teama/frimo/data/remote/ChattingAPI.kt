package gachon.teama.frimo.data.remote

import gachon.teama.frimo.data.entities.ChattingDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ChattingAPI {
    // 단어 추가하는 API
    @POST("chatting/{userPk}")
    fun chat(@Body word:ChattingDto) : Call<String>
}