package gachon.teama.frimo.retrofit.dao;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    private Long diaryPk;
    private String diaryTitle;
    private String diaryContent;
    private String imagePath;
    private User author; // userPk
    private LocalDateTime dateCreated;
    private int dateCreatedYear;
    private int dateCreatedMonth;
    private int mainSent;

    @Builder
    public Diary(String diaryTitle, String diaryContent, String imagePath, User author, LocalDateTime dateCreated,
                 int mainSent, int dateCreatedYear, int dateCreatedMonth) {
        this.diaryContent = diaryContent;
        this.diaryTitle = diaryTitle;
        this.imagePath = imagePath;
        this.author = author;
        this.dateCreated = dateCreated;
        this.mainSent = mainSent;
        this.dateCreatedYear = dateCreatedYear;
        this.dateCreatedMonth = dateCreatedMonth;
    }
}
