package helloandroid.ut3.minigame.models;

import java.util.Date;

public class Score {
    private Date date;
    private int score;

    public Score(Date date, int score) {
        this.date = date;
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
