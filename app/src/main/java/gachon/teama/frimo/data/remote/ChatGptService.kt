package gachon.teama.frimo.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

interface ChatGptService {

    // (다빈치 모델) ChatGpt에게 요청하면 그에 대한 응답을 받는 API
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${ChatGptDavinci.apiKey}"
    )
    @POST("https://api.openai.com/v1/engines/davinci/completions")
    suspend fun getCompletion(
        @Body request: DavinciRequest,
    ): DavinciResponse

    data class DavinciRequest(
        @SerializedName("prompt")
        val prompt: String,
        @SerializedName("max_tokens")
        val maxTokens: Int = 200
    )

    data class DavinciResponse(
        @SerializedName("choices") val choices: List<DavinciChoice>
    )

    data class DavinciChoice(
        @SerializedName("text") val text: String,
        @SerializedName("index") val index: Int,
        @SerializedName("logprobs") val logProbs: Any?,
        @SerializedName("finish_reason") val finishReason: String
    )

}