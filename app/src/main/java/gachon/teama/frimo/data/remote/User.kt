package gachon.teama.frimo.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("userPk") val pk: Long,
    @SerializedName("userId") val id: String,
    @SerializedName("userNN") val nickname: String
) : Parcelable

//import java.io.Serializable;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class User implements Serializable {
//    private Long userPk;
//    private String userId;
//    private String userNN;
//
//}