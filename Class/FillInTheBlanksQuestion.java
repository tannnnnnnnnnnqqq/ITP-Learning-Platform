package src.Class;

import javax.swing.*;

import src.Interface.Question;

import java.awt.*;

public class FillInTheBlanksQuestion implements Question {
    private String questionText;
    private String correctAnswer;
    private JTextField answerField;
    
    private String userAnswer;

    public FillInTheBlanksQuestion(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public JPanel getQuestionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel questionLabel = new JLabel("<html>" + questionText + "</html>");
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setPreferredSize(new Dimension(300, 50)); // Set preferred size to handle text overflow
        panel.add(questionLabel, BorderLayout.NORTH);

        answerField = new JTextField();
        if (userAnswer != null) {
            answerField.setText(userAnswer);
        }
        panel.add(answerField, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public boolean checkAnswer() {
        userAnswer = answerField.getText().trim();
        return userAnswer.equalsIgnoreCase(correctAnswer);
    }

    @Override
    public void saveAnswer() {
        userAnswer = answerField.getText().trim();
    }

    @Override
    public void loadAnswer() {
        if (userAnswer != null) {
            answerField.setText(userAnswer);
        }
    }

    @Override
    public void resetAnswer() {
        userAnswer = "";
        if (answerField != null) {
            answerField.setText("");
        }
    }
}
