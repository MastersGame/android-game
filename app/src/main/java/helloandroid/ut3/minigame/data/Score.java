package helloandroid.ut3.minigame.data;

import java.util.Date;

public class Score {
    private Date date;
    private int score;

    private int nbTentatives;

    public Score(Date date, int score, int nbTentatives) {
        this.date = date;
        this.score = score;
        this.nbTentatives = nbTentatives;
    }

    public int getNbTentatives() {
        return nbTentatives;
    }

    public void setNbTentatives(int nbTentatives) {
        this.nbTentatives = nbTentatives;
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
