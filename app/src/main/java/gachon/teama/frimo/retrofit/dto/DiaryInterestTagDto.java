package gachon.teama.frimo.retrofit.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiaryInterestTagDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddTagRequestDto {

        private Long diaryPk;
        private String tagContent;
        private Long sentPK;
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
    public static class GetTagResponseDto {
        private String tagContent;
        private int sentLargeId; // 0~5

        @Builder
        public GetTagResponseDto(String tagContent, int sentLargeId) {

            this.tagContent = tagContent;
            this.sentLargeId = sentLargeId;
        }
    }
}
