package gachon.teama.frimo.data.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiaryInterestTagDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddTagRequestDto { // POST

        private Long diaryPk;
        private String tagContent;
        private Long sentPK; //10,19,28,37,46
        private String category;

        @Builder
        public AddTagRequestDto(Long diaryPk, String tagContent, Long sentPk, String category) {
            this.diaryPk = diaryPk;
            this.tagContent = tagContent;
            this.sentPK = sentPk;
            this.category = category;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetTagResponseDto {  // GET
        private String tagContent;
        private int sentLargeId; // 0~5
        private Long diaryPk;
        private Long sentPk;
        private String category;

    }

}
