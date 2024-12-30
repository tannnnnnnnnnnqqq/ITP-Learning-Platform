package src.Component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.Class.Score;
import src.Dao.ScoreDAO;
import src.Interface.ContentPanel;

public class ScoreboardPanel extends JPanel implements ContentPanel {

    private static JLabel scoreLabel;
    private JTextArea scoreArea;
    private JButton refreshButton;
    private JScrollPane scrollPane;

    public ScoreboardPanel() {
        
        setLayout(new BorderLayout());

        scoreLabel = new JLabel("Scores: ");
        add(scoreLabel, BorderLayout.NORTH);
        refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent();
            }
        });

        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scrollPane = new JScrollPane(scoreArea);
        add(scrollPane, BorderLayout.CENTER);

        updateContent();
    }

    @Override
    public void updateContent() {

        ScoreDAO scoreDAO = new ScoreDAO();
        List<Score> scores = scoreDAO.getAllScores();

        StringBuilder scoreText = new StringBuilder();
        int totalScore = 0;
        if(scores == null || scores.isEmpty()){
            scoreText.append("***Scoreboard is empty***");
            scoreArea.setText(scoreText.toString());
            return;
        }

        for (Score score : scores) {
            scoreText.append("Date/Time: ").append(score.getDatetime()).append("\n")
                    .append("Student Name: ").append(score.getStudentName()).append("\n")
                    .append("Score: ").append(score.getScore()).append("\n")
                    .append("Percentage: ").append(score.getPercentage()).append("\n")
                    .append("Message: ").append(score.getScoreMessage()).append("\n")
                    .append("Time to Complete: ").append(score.getTimeToComplete()).append(" seconds\n")
                    .append("------------------------------\n");
            totalScore += score.getScore();
        }
        scoreArea.setText(scoreText.toString());
        scoreLabel.setText("Total Scores: " + totalScore);
    }

    @Override
    public void resetContent() {
        throw new UnsupportedOperationException("Unimplemented method 'resetContent'");
    }
}