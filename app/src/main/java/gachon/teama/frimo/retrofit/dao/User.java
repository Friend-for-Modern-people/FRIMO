package gachon.teama.frimo.retrofit.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private Long userPk;
    private String userId;
    private String userNN;

    @Builder
    public User(String userId, String userNN) {
        this.userId = userId;
        this.userNN = userNN;
    }


}
