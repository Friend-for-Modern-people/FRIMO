package gachon.teama.frimo.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatGptService {

    // ChatGpt에게 요청하면 그에 대한 응답을 받는 API
    @POST("https://api.openai.com/v1/engines/davinci/completions")
    suspend fun getCompletion(
        @Body request: ChatGptRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authorization: String
    ): ChatGptResponse

    data class ChatGptRequest(
        @SerializedName("prompt")
        val prompt: String,
        @SerializedName("max_tokens")
        val maxTokens: Int = 200
    )

    data class ChatGptResponse(
        @SerializedName("choices")
        val choices: List<Choice>
    )

    data class Choice(
        @SerializedName("text")
        val text: String,
        @SerializedName("index")
        val index: Int,
        @SerializedName("logprobs")
        val logProbs: Any?,
        @SerializedName("finish_reason")
        val finishReason: String
    )

}