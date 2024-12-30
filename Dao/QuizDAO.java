package src.Dao;

import src.Class.*;
import src.Interface.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private static final String FILE_PATH = "src/Dao/Quiz.txt";

    public List<Question> getAllQuizzes() {
        List<Question> questions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String type = parts[0];
                String questionText = parts[1];

                switch (type) {
                    case "MCQ":
                        String[] options = parts[2].split(",");
                        String correctAnswer = parts[3];
                        questions.add(new MultipleChoiceQuestion(questionText, options, correctAnswer));
                        break;
                    case "FITB":
                        String correctAnswerFITB = parts[2];
                        questions.add(new FillInTheBlanksQuestion(questionText, correctAnswerFITB));
                        break;
                    case "TF":
                        boolean correctAnswerTF = Boolean.parseBoolean(parts[2]);
                        questions.add(new TrueFalseQuestion(questionText, correctAnswerTF));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
