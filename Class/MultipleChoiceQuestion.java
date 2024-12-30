package src.Class;

import javax.swing.*;

import src.Interface.Question;

import java.awt.*;
import java.util.Enumeration;

public class MultipleChoiceQuestion implements Question {
    private String[] options;
    private String correctAnswer;
    private String questionText;
    private ButtonGroup group;
    private String selectedAnswer;

    public MultipleChoiceQuestion(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public JPanel getQuestionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel questionLabel = new JLabel("<html>" + questionText + "</html>");
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setPreferredSize(new Dimension(300, 50)); // Set preferred size to handle text overflow
        panel.add(questionLabel, BorderLayout.NORTH);

        group = new ButtonGroup();
        JPanel optionsPanel = new JPanel(new GridLayout(options.length, 3));
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            group.add(radioButton);
            optionsPanel.add(radioButton);
            if (option.equals(selectedAnswer)) {
                radioButton.setSelected(true);
            }
        }
        panel.add(optionsPanel, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public boolean checkAnswer() {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected() && button.getText().equals(correctAnswer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveAnswer() {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                selectedAnswer = button.getActionCommand();
            }
        }
    }

    @Override
    public void loadAnswer() {
        if (selectedAnswer != null) {
            for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.getActionCommand().equals(selectedAnswer)) {
                    button.setSelected(true);
                }
            }
        }
    }

    @Override
    public void resetAnswer() {
        selectedAnswer = null; 
        if (group != null) {
            group.clearSelection(); 
        }
    }
}
