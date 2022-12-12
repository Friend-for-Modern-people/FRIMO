package gachon.teama.frimo.data.entities;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegistRequestDto {
        private String userId;
        private String userNN;

        @Builder
        public RegistRequestDto(String userId, String userNN) {
            this.userId = userId;
            this.userNN = userNN;

        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserOnlyInfoResponseDto { // DB에서 가져온 댓글을 DTO로 변환하기 위한 DTO
        private Long userPk;
        private String userId;
        private String userNN;

    }




}
