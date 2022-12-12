package gachon.teama.frimo.data.entities;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingDto {
    //private String who;
    private String message;
    //private Date time;

    @Builder
    public ChattingDto( String message) {
        this.message = message;
    }
}
