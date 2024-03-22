package helloandroid.ut3.minigame.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import helloandroid.ut3.minigame.R;

public class ScoreCardView extends CardView {

    private TextView valueView;
    private TextView dateView;

    public ScoreCardView(Context context) {
        super(context);
        init();
    }

    public ScoreCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScoreCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.score_card, this);
        valueView = findViewById(R.id.score_value);
        dateView = findViewById(R.id.score_date);
    }

    public TextView getScoreValue() {
        return valueView;
    }

    public void setScoreValue(int value) {
        valueView.setText(String.valueOf(value));
    }

    public TextView getScoreDate() {
        return dateView;
    }

    public void setScoreDate(String date) {
        dateView.setText(date);
    }

    public void setScoreNbTentatives(int nbTentatives) {
        valueView.setText(String.valueOf(nbTentatives) + " tentatives");
    }
}
