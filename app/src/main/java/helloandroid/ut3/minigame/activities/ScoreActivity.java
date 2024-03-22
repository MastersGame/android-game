package helloandroid.ut3.minigame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.models.Score;
import helloandroid.ut3.minigame.services.ScoreService;
import helloandroid.ut3.minigame.views.ScoreCardView;

public class ScoreActivity extends AppCompatActivity {

    private ScoreService scoreService;
    private List<Score> scores;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ScoreService.instanciate(this);
        scoreService = ScoreService.getInstance();
        scoreService.addScore(new Score(new Date(), "10"));
        scoreService.addScore(new Score(new Date(), "30"));
        scoreService.addScore(new Score(new Date(), "120"));
        scores = scoreService.getScores();
        filterButton = findViewById(R.id.filter_button);

        MaterialDatePicker<Pair<Long, Long>> dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .build();
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangePicker.show(getSupportFragmentManager(), "tag");
                List<Score> filteredScores = filterScoresByDateRange(scores, dateRangePicker.getSelection());
            }
        });
        displayScores();
    }

    private void displayScores() {
        LinearLayout scoreContainer = findViewById(R.id.scores_container);
        scoreContainer.removeAllViews();
        for (Score score : scores) {
            ScoreCardView cardView = new ScoreCardView(this);
            cardView.setScoreValue(score.getScore());
            cardView.setScoreDate(score.getDate().toString());
            scoreContainer.addView(cardView);
        }
    }

    public List<Score> filterScoresByDateRange(List<Score> scores, Pair<Long, Long> selectedRange) {

        if (selectedRange == null) {
            return scores;
        }
        long startDate = 0;
        long endDate = 0;
        if (selectedRange.first != null) {
            startDate = selectedRange.first;
        }
        if (selectedRange.second != null) {
            endDate = selectedRange.second;
        }

        List<Score> filteredScores = new ArrayList<>();
        for (Score score : scores) {
            long scoreTimeStamp = score.getDate().getTime();
            if (scoreTimeStamp >= startDate && scoreTimeStamp <= endDate) {
                filteredScores.add(score);
            }
        }
        return filteredScores;
    }
}
