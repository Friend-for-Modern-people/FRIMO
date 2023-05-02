package gachon.teama.frimo.data.remote.chatGpt

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGptService {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${ChatGpt.apiKey}"
    )
    @POST("v1/chat/completions")
    suspend fun getCompletion(
        @Body request: ChatGptRequest
    ): ChatGptResponse

    data class ChatGptRequest(
        @SerializedName("model") val model: String,
        @SerializedName("messages") val messages: List<Message>,
        @SerializedName("temperature") val temperature: Double
    )

    data class Message(
        @SerializedName("role") val role: String,
        @SerializedName("content") val content: String
    )

    data class ChatGptResponse(
        val id: String,
        val `object`: String,
        val created: Long,
        val model: String,
        val usage: Usage,
        val choices: List<Choice>
    )

    data class Usage(
        val prompt_tokens: Int,
        val completion_tokens: Int,
        val total_tokens: Int
    )

    data class Choice(
        val message: Message,
        val finish_reason: String,
        val index: Int
    )
}