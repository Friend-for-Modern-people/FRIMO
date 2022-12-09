package gachon.teama.frimo.retrofit.dao;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryInterestTag {
    private Long tagId;
    private Diary diary;
    private SentimentTag sentimentTag;
    private String tagContent;
    private String category;

    @Builder
    public DiaryInterestTag(String tagContent, String category, Diary diary, SentimentTag sentimentTag) {
        this.tagContent = tagContent;
        this.category = category;
        this.diary = diary;
        this.sentimentTag = sentimentTag;
    }

}
