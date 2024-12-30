package src.Class;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import src.Interface.Scoreable;

public class Score implements Scoreable {
    private String datatime;
    private String studentName;
    private String scoreMessage;
    private int score;
    private int percentage;
    private int timeToComplete;

    public Score() {
        this.score = 0;
        this.percentage = 0;
        this.timeToComplete = 0;
    }

    public Score(String datatime, String studentName, int score, int percentage, String scoreMessage,
            int timeToComplete) {
        this.score = score;
        this.datatime = datatime;
        this.percentage = percentage;
        this.scoreMessage = scoreMessage;
        this.studentName = studentName;
        this.timeToComplete = timeToComplete;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void increaseScore() {
        score++;
    }

    @Override
    public void resetScore() {
        score = 0;
    }

    public String getDatetime() {
        return datatime;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getScoreMessage() {
        return scoreMessage;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public void calculateScore(int totalQuestions) {
        if (totalQuestions > 0) {
            percentage = (int) (((double) score / totalQuestions) * 100.0);
        } else {
            percentage = 0;
        }

        System.out.println("Percentage Score: " + percentage + " %");
        String scoreMessage = determineScoreMessage(percentage);
        System.out.println("Score Message: " + scoreMessage);
    }

    public int getPercentage() {
        return percentage;
    }

    public String determineScoreMessage(double percentageScore) {
        if (percentageScore >= 80 && percentageScore <= 100) {
            return "Outstanding!";
        } else if (percentageScore >= 60 && percentageScore <= 79) {
            return "That's good!";
        } else if (percentageScore >= 40 && percentageScore <= 59) {
            return "Good try!";
        } else if (percentageScore >= 20 && percentageScore <= 39) {
            return "You can do better!";
        } else if (percentageScore >= 0 && percentageScore <= 19) {
            return "Don't give up!";
        } else {
            return "Invalid score";
        }
    }

    public void saveScoreToFile() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        String scoreRecord = formattedDateTime + "|" + studentName + "|" + score + "|" + percentage + "|"
                + determineScoreMessage(percentage) + "|" + timeToComplete + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Dao/Score.txt", true))) {
            writer.write(scoreRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}