package gachon.teama.frimo.data.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gachon.teama.frimo.retrofit.dao.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiaryDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetDiaryResponseDto {
        //일기(최신순)으로 가져오기
        private Long diaryPk;
        private String diaryTitle;
        private String diaryContent;
        private User user; // userPk
        private LocalDateTime dateCreated;
        private String dateCreatedinString;
        private int dateCreatedYear;
        private int dateCreatedMonth;
        // 제일 빈도 수 많은 감정을 뽑기 -> 별도의 함수 만들기
        private int mainSent;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddDiaryRequestDto {

        private String diaryTitle;
        private String diaryContent;
        private Long userPk; // userPk로 service단에서 user찾기
        private LocalDateTime dateCreated;

        @Builder
        public AddDiaryRequestDto(String diaryTitle, String diaryContent, Long userPk, LocalDateTime dateCreated ) {
            this.diaryTitle = diaryTitle;
            this.diaryContent = diaryContent;
            this.userPk = userPk;
            this.dateCreated = dateCreated;
        }

    }
}
