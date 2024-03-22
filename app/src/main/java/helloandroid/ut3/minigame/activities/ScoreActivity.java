package helloandroid.ut3.minigame.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private List<Score> allScores;
    private List<Score> filteredScores;
    private LinearLayout scoreContainer;
    private TextView selectedDatesText;
    private long startDate = 0;
    private long endDate = Long.MAX_VALUE;
    private EditText editText;
    private int maxScore = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ScoreService.instanciate(this);
        scoreService = ScoreService.getInstance();
        allScores = scoreService.getScores();
        filteredScores = new ArrayList<>(allScores);

        scoreContainer = findViewById(R.id.scores_container);
        selectedDatesText = findViewById(R.id.selected_dates);

        selectedDatesText.setText(getString(R.string.all_scores));

        MaterialDatePicker<Pair<Long, Long>> dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .build();

        selectedDatesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateRangePicker.show(getSupportFragmentManager(), "tag");

                dateRangePicker.addOnPositiveButtonClickListener(selection -> {
                    if (selection != null) {
                        startDate = selection.first;
                        endDate = selection.second;
                        updateSelectedDatesText();
                        filterScores();
                    }
                });
            }
        });

        editText = findViewById(R.id.filter_time_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    maxScore = -1;
                } else {
                    maxScore = Integer.parseInt(s.toString());
                }
                filterScores();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        displayScores();
    }

    private void updateSelectedDatesText() {
        String selectedDates;
        if (startDate == 0 && endDate == Long.MAX_VALUE) {
            selectedDates = getString(R.string.all_scores);
        } else {
            selectedDates = "Du " + formatDate(startDate) + " au " + formatDate(endDate);
        }
        selectedDatesText.setText(selectedDates);
    }

    private String formatDate(long timestamp) {
        return DateFormat.format("dd/MM/yyyy", new Date(timestamp)).toString();
    }

    private String formatDateAndHours(long timestamp) {
        return DateFormat.format("dd/MM/yyyy HH:mm", new Date(timestamp)).toString();
    }

    private void filterScores() {
        filteredScores.clear();
        for (Score score : allScores) {
            long scoreTimeStamp = score.getDate().getTime();
            boolean dateCondition = (startDate == 0 && endDate == Long.MAX_VALUE) || (scoreTimeStamp >= startDate && scoreTimeStamp <= endDate);
            boolean scoreCondition = maxScore == -1 || score.getScore() <= maxScore;
            if (dateCondition && scoreCondition) {
                filteredScores.add(score);
            }
        }
        displayScores();
    }

    private void displayScores() {
        scoreContainer.removeAllViews();
        for (Score score : filteredScores) {
            ScoreCardView cardView = new ScoreCardView(this);
            cardView.setScoreValue(score.getScore());
            cardView.setScoreDate(formatDateAndHours(score.getDate().getTime()));
            scoreContainer.addView(cardView);
        }
    }
}
