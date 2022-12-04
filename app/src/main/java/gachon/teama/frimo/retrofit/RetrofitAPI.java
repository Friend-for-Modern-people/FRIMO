package gachon.teama.frimo.retrofit;

import com.google.firebase.database.core.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @GET("/app/chatting") // 데이터를 가져오르 특정 API주소
    Call<List<Repo>> listRepos(@Path("user") String user);
}
