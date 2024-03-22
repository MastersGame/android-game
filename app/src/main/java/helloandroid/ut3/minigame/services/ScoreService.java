package helloandroid.ut3.minigame.services;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import helloandroid.ut3.minigame.models.Score;

public class ScoreService {

    private static volatile ScoreService instance;
    private static volatile Context context;

    public ScoreService(Context context) {
        ScoreService.context = context;
        scores = new ArrayList<>();
        loadScoresFromFile();
    }

    public static ScoreService getInstance() {
        if (instance == null)
            throw new NullPointerException("This service was not instanciate");
        return instance;
    }

    public static void instanciate(Context context) {
        if (instance == null) {
            synchronized (ScoreService.class) {
                if (instance == null) {
                    instance = new ScoreService(context);
                }
            }
        }
    }

    private static final String FILE_NAME = "scores.txt";

    private List<Score> scores;

    public void addScore(Score score) {
        scores.add(score);
        saveScoresToFile();
    }

    public List<Score> getScores() {
        return scores;
    }

    private void loadScoresFromFile() {
        try (Scanner scanner = new Scanner(new File(ScoreService.context.getFilesDir(), FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                scores.add(new Score(new Date(Long.parseLong(parts[0])), Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveScoresToFile() {
        try (PrintWriter writer = new PrintWriter(new File(ScoreService.context.getFilesDir(), FILE_NAME))) {
            for (Score score : scores) {
                writer.println(score.getDate().getTime() + "," + score.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
