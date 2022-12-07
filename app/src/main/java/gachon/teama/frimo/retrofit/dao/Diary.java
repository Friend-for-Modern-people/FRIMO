package gachon.teama.frimo.retrofit.dao;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Setter;

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


    private List<DiaryInterestTag> tags;
   // private List<CommentAI> comments;

}
