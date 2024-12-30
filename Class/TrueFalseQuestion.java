package src.Class;

import javax.swing.*;

import src.Interface.Question;

import java.awt.*;

public class TrueFalseQuestion implements Question {
    private boolean correctAnswer;
    private String questionText;
    private JRadioButton trueButton;
    private JRadioButton falseButton;
    private ButtonGroup group;
    private Boolean userAnswer = null;

    public TrueFalseQuestion(String questionText, boolean correctAnswer) {
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

        trueButton = new JRadioButton("True");
        falseButton = new JRadioButton("False");

        group = new ButtonGroup();
        group.add(trueButton);
        group.add(falseButton);

        

        System.out.println(userAnswer);

        JPanel optionsPanel = new JPanel(new GridLayout(1, 2));
        optionsPanel.add(trueButton);
        optionsPanel.add(falseButton);

        panel.add(optionsPanel, BorderLayout.CENTER);

        loadAnswer();

        return panel;
    }

    @Override
    public boolean checkAnswer() {
        userAnswer = trueButton.isSelected();
        return userAnswer == correctAnswer;
    }

    @Override
    public void saveAnswer() {
        userAnswer = trueButton.isSelected();
    }

    @Override
    public void loadAnswer() {
        if (userAnswer != null) {
            trueButton.setSelected(userAnswer);
            falseButton.setSelected(!userAnswer);
        } 
    }

    @Override
    public void resetAnswer() {
        userAnswer = null; // Clear the stored answer
        if (group != null) {
            group.clearSelection(); // Clear the selection in the button group
        }
    }
}
