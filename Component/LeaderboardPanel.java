package src.Component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import src.Class.Score;
import src.Dao.ScoreDAO;
import src.Interface.ContentPanel;

public class LeaderboardPanel extends JPanel implements ContentPanel {

    private JPanel scorePanel;
    private List<Score> scores;
    private JButton refreshButton;
   

    public LeaderboardPanel() {
        setLayout(new BorderLayout());

        // Header panel to dynamically display the top score information
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel();
        headerPanel.add(headerLabel);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent();
            }
        });

        // Initialize the main panel to display the scores
        scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(scorePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.SOUTH);

        // Sort scores and display leaderboard
        sortScores();
    }

    private void sortScores() {
        ScoreDAO scoreDAO = new ScoreDAO();
        List<Score> scores = scoreDAO.getAllScores();
        
        // Sort scores based on score in descending order
        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));

        // Remove all previous components
        scorePanel.removeAll();

        if(scores.isEmpty() || scores == null){
            JLabel warningLabel = new JLabel("Leaderboard is empty");
            scorePanel.add(warningLabel);
            return;
        }

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);

            JLabel nameLabel = new JLabel(); 
            JLabel scoreLabel = new JLabel();
            JPanel singleScorePanel = new JPanel();

            singleScorePanel.setLayout(new GridLayout(2, 1));
            singleScorePanel.setBorder(BorderFactory.createEtchedBorder());
            singleScorePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Set max height for each panel

            nameLabel.setText((i + 1) + ". Student Name: " + score.getStudentName());
            scoreLabel.setText("Score: " + score.getScore());

            singleScorePanel.add(nameLabel);
            singleScorePanel.add(scoreLabel);

            scorePanel.add(singleScorePanel);

            // Display top score in header
            if (i == 0) {
                JLabel headerLabel = (JLabel) ((JPanel) this.getComponent(0)).getComponent(0);
                headerLabel.setText("Top Score: " + score.getScore() + "   Student Name : " + score.getStudentName());
            }
        }

        // Refresh the panel to show the updated scores
        scorePanel.revalidate();
        scorePanel.repaint();
    }

    @Override
    public void updateContent() {
        // Update content implementation
        sortScores();
    }

    @Override
    public void resetContent() {
        // Reset content implementation
        scores.clear();
        scorePanel.removeAll();
        scorePanel.revalidate();
        scorePanel.repaint();
    }
}
