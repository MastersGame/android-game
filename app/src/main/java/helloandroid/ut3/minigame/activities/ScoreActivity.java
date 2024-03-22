package helloandroid.ut3.minigame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.models.Score;
import helloandroid.ut3.minigame.models.ScoreAdapter;
import helloandroid.ut3.minigame.services.ScoreService;

public class ScoreActivity extends AppCompatActivity {

    private ScoreService scoreService;
    private List<Score> scores;
    private RecyclerView scoreList;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ScoreService.instanciate(this);
        scoreService = ScoreService.getInstance();
        scores = scoreService.getScores();

        scoreList = findViewById(R.id.score_list);
        scoreList.setAdapter(new ScoreAdapter(scores));

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
                scoreList.setAdapter(new ScoreAdapter(filteredScores));
            }
        });
    }

    public List<Score> filterScoresByDateRange(List<Score> scores, Pair<Long, Long> selectedRange) {

        if (selectedRange == null) {
            return scores;
        }
        long startDate = 0;
        long endDate = 0;
        if (selectedRange.first!= null) {
            startDate = selectedRange.first;
        }
        if (selectedRange.second!= null) {
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
