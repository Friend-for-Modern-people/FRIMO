package gachon.teama.frimo.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object ChatServer {
    private const val chatUrl = "http://server.vivi108.com/"

    private val client = OkHttpClient.Builder().build()
    private val chatRetrofit = Retrofit.Builder()
        .baseUrl(chatUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(client)
        .build()

    private val chatApi: ChattingAPI = chatRetrofit.create(ChattingAPI::class.java)

    // ---------- Chat API ----------

    // 유저가 chatbot에게 message를 보내면 그에 대한 응답을 받아오는 API
    suspend fun getMessage(message: String): String = withContext(Dispatchers.IO) {
        chatApi.getMessage(message).let { response ->
            response.takeIf { it.isSuccessful }?.body()
                ?: throw Exception(response.errorBody()?.charStream()?.readText())
        }
    }
}