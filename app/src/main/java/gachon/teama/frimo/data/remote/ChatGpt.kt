package gachon.teama.frimo.data.remote

import android.util.Log
import gachon.teama.frimo.data.remote.ChatGptService.ChatGptRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChatGpt {

    private const val apiKey = "<api-key>"
    private val chatGptService = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChatGptService::class.java)

    // ---------- Chat Gpt ----------

    // 채팅 서버로부터 온 응답의 어미를 바꾸는 API
    suspend fun getChangedSentence(prompt: String, friendId: Int): String =
        withContext(Dispatchers.IO) {
            val newPrompt = updatePrompt(prompt, friendId)
            Log.d("ChagtGpt", "\"$newPrompt\" 문장으로 request")

            chatGptService.getCompletion(request = ChatGptRequest(newPrompt), authorization = "Bearer $apiKey").let { response ->
                response.choices[0].text
            }
        }

    // 채팅하는 친구의 성격에 맞게 ChatGpt에 요청할 문장 수정
    private fun updatePrompt(prompt: String, friendId: Int): String {
        return "$prompt\n\n위 문장을" + when (friendId) {
            1 -> "따뜻하고 포근한 느낌으로"
            2 -> "따뜻하고 친숙한 느낌으로"
            3 -> "친숙하고 존경해주는 느낌으로"
            4 -> "차분하고 따뜻한 느낌으로"
            5 -> "차분하고 따뜻하며 존경해주는 느낌으로"
            6 -> "친숙한 느낌으로"
            7 -> "따뜻하고 친숙한 느낌으로"
            else -> ""
        } + "바꿔줘"
    }

}