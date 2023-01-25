package gachon.teama.frimo.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var instance: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    // 서버 주소
    private const val BASE_URL = "http://218.48.213.10:80/app/"

    // SingleTon
    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        return instance!!
    }
}