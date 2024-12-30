package src.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.Class.Score;

public class ScoreDAO {

    private static final String FILE_PATH = "src/Dao/Score.txt";

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String datetime = parts[0];
                    String studentName = parts[1];
                    int score = Integer.parseInt(parts[2]);
                    int percentage = Integer.parseInt(parts[3]);
                    String scoreMessage = parts[4];
                    int timeToComplete = Integer.parseInt(parts[5]);

                    scores.add(new Score(datetime, studentName, score, percentage, scoreMessage, timeToComplete));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }
}
