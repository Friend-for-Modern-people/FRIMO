package gachon.teama.frimo.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {  //used in SingleTon
    private static RetrofitClient instance = null;
    private static RetrofitAPI retrofitAPI;

    private final static String BASE_URL ="";

    private RetrofitClient(){
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//Json데이터를 사용자가 정의한 Java 객채로 변환해주는 라이브러리
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }
    public static RetrofitClient getInstance(){
        if (instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }
    public static RetrofitAPI getRetrofitAPI(){
        return  retrofitAPI;
    }
}
