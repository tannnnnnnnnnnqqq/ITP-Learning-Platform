package src;

import javax.swing.*;

import src.Component.*;
import src.Class.*;
import src.Dao.*;
import src.Interface.Question;

import java.util.List;

public class Main extends JFrame {

    public Main() {

        setTitle("ITP Learning App");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // get lesson, quiz, score from txt
        LessonDAO lessonDAO = new LessonDAO();
        List<Lesson> lessons = lessonDAO.getAllLessons();

        QuizDAO quizDAO = new QuizDAO();
        List<Question> quizzes = quizDAO.getAllQuizzes();

        // ScoreDAO scoreDAO = new ScoreDAO();
        // List<Score> scores = scoreDAO.getAllScores();

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Lessons Panel with pagination
        LessonPanel lessonPanel = new LessonPanel(lessons);
        tabbedPane.addTab("Lessons", lessonPanel);

        // Quiz Panel with pagination
        QuizPanel quizPanel = new QuizPanel(quizzes);
        tabbedPane.addTab("Quizzes", quizPanel);

        // Scoreboard Panel
        ScoreboardPanel scoreboardPanel = new ScoreboardPanel();
        tabbedPane.addTab("Scoreboard", scoreboardPanel);

        // Leaderboard Panel
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
        tabbedPane.addTab("Leaderboard", leaderboardPanel);

        // Add tabbed pane to frame
        add(tabbedPane);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });

    }
}
