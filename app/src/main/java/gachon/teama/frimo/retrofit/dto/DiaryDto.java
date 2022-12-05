package gachon.teama.frimo.retrofit.dto;

import java.time.LocalDateTime;


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
        private int dateCreatedYear;
        private int dateCreatedMonth;
        // 제일 빈도 수 많은 감정을 뽑기 -> 별도의 함수 만들기
        private int mainSent;

        @Builder
        public GetDiaryResponseDto(Long diaryPk, String diaryTitle, String diaryContent, User user, LocalDateTime dateCreated, int dateCreatedYear,int dateCreatedMonth, int mainSent){
            this.diaryPk=diaryPk;
            this.diaryTitle = diaryTitle;
            this.diaryContent = diaryContent;
            this.user = user;
            this.dateCreated = dateCreated;
            this.dateCreatedMonth= dateCreatedMonth;
            this.dateCreatedYear = dateCreatedYear;
            this.mainSent= mainSent;
        }

    }
}
