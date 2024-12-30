package src.Component;

import src.Interface.Question;
import src.Class.Score;
import src.Interface.ContentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuizPanel extends JPanel implements ContentPanel {
    private List<Question> questions;
    private int currentPage = -1;
    private JTextField nameField;
    private JPanel questionPanel;
    private JPanel firstPage;
    private JLabel titleLabel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton submitButton;
    private JButton resetButton;
    private Score scoreBoard;
    private String studentName;
    private JLabel timerLabel;
    private long startTime;
    private long endTime;
    private Timer timer;

    public QuizPanel(List<Question> questions) {
        this.questions = questions;
        this.scoreBoard = new Score();
        setLayout(new BorderLayout());

        questionPanel = new JPanel();
        timerLabel = new JLabel("Time: 00:00:00", SwingConstants.CENTER);
        titleLabel = new JLabel("");
        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset quiz");

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage < questions.size() - 1) {
                    saveAnswer();
                    currentPage++;
                    updateContent();
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    saveAnswer();
                    currentPage--;
                    updateContent();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTime = System.currentTimeMillis();
                scoreBoard.resetScore();
                scoreBoard.setStudentName(studentName);

                for (Question question : questions) {
                    if (question.checkAnswer()) {
                        scoreBoard.increaseScore();
                    }
                }

                scoreBoard.calculateScore(questions.size());
                scoreBoard.setTimeToComplete((int) ((endTime - startTime) / 1000));
                scoreBoard.saveScoreToFile();

                String message = "<html>You got " + scoreBoard.getScore() + " out of " + questions.size()
                        + " correct.<br>" +
                        "Percentage Score: " + scoreBoard.getPercentage() + "%<br>" +
                        "Score Message: " + scoreBoard.determineScoreMessage(scoreBoard.getPercentage()) + "<br>" +
                        "Time to Complete: " + scoreBoard.getTimeToComplete() + " seconds</html>";

                JOptionPane.showMessageDialog(QuizPanel.this, message);

                resetContent();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetContent();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(timerLabel, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (!questions.isEmpty()) {
            updateContent();
        } else {
            questionPanel.add(new JLabel("No questions available."));
        }

        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        submitButton.setEnabled(false);
    }

    @Override
    public void updateContent() {

        questionPanel.removeAll();
        
        if (currentPage == -1) {
            buildFirstPage();
        } else {
            Question question = questions.get(currentPage);
            question.loadAnswer();
            titleLabel.setText("<html><h1> Question " + (currentPage + 1) + "</h1></html>");
            questionPanel.add(question.getQuestionPanel());
            questionPanel.revalidate();
            questionPanel.repaint();

            prevButton.setEnabled(currentPage > 0);
            nextButton.setEnabled(currentPage < questions.size() - 1);
            submitButton.setEnabled(currentPage == questions.size() - 1);

        }

    }

    @Override
    public void resetContent() {
        //stop the timer and reset
        if (timer != null) {
            timer.stop();
        }

        // Reset the timer label
        timerLabel.setText("Time: 00:00:00");
        titleLabel.setText("");

        for (Question question : questions) {
            question.resetAnswer();
        }
        currentPage = -1;
        updateContent();
        nextButton.setEnabled(false);
        prevButton.setEnabled(false);
        submitButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    private void buildFirstPage() {
        // Create a JPanel with GridBagLayout for better control of components placement
        firstPage = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        JLabel label = new JLabel("Please enter your name before starting the quiz");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        firstPage.add(label, gbc);

        // Create the input field
        nameField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        firstPage.add(nameField, gbc);

        // Create a button to proceed
        JButton startButton = new JButton("Start Quiz");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText() == null || nameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(firstPage, "Name cannot be empty !", "Alert",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                currentPage = 0;
                studentName = nameField.getText();
                startTime = System.currentTimeMillis();
                startTimer();
                updateContent();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        firstPage.add(startButton, gbc);

        // Add firstPage panel to the frame
        questionPanel.add(firstPage, BorderLayout.CENTER);
    }

     private void startTimer() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startDate = new Date(startTime);
        timerLabel.setText("Start Time: " + sdf.format(startDate) + " | Time: 00:00:00");

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                int seconds = (int) (elapsedTime / 1000) % 60;
                int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
                int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
                timerLabel.setText("Start Time: " + sdf.format(startDate) + " | Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
            }
        });
        timer.start();
    }

    private void saveAnswer() {
        questions.get(currentPage).saveAnswer();
    }
}
