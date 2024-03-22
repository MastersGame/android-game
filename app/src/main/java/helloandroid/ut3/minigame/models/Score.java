package helloandroid.ut3.minigame.models;

import java.util.Date;

public class Score {
    private Date date;
    private String score;

    public Score(Date date, String score) {
        this.date = date;
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
