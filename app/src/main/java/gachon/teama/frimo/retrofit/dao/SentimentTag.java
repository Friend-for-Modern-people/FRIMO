package gachon.teama.frimo.retrofit.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentimentTag {
    private Long sentPk;
    private int sentLargeId;
    private String sentSmallId;

    @Builder
    public SentimentTag(int sentLargeId, String sentSmallId){
        this.sentLargeId = sentLargeId;
        this.sentSmallId = sentSmallId;
    }
}
