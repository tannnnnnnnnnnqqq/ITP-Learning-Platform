package src.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import src.Class.*;
import src.Interface.ContentPanel;

public class LessonPanel extends JPanel implements ContentPanel{
    private List<Lesson> lessons;
    private int currentPage = 0; 
    private JLabel titleLabel;
    private JTextPane contentPane;
    //private JTextArea contentArea;
    private JLabel imageLabel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton resetButton;

    public LessonPanel(List<Lesson> lessons) {
        this.lessons = lessons;
        setLayout(new BorderLayout());

        titleLabel = new JLabel("", JLabel.CENTER);
        contentPane = new JTextPane();
        contentPane.setContentType("text/html");
        contentPane.setEditable(false);
        // contentArea = new JTextArea();
        // contentArea.setWrapStyleWord(true);
        // contentArea.setLineWrap(true);
        // contentArea.setEditable(false);

        imageLabel = new JLabel("", JLabel.CENTER);

        nextButton = new JButton("Next");
        prevButton = new JButton("Previous");
        resetButton = new JButton("Reset");

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage < lessons.size() - 1) {
                    currentPage++;
                    updateContent();
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    updateContent();
                }
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
        buttonPanel.add(resetButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new JScrollPane(contentPane), BorderLayout.CENTER);
        contentPanel.add(imageLabel, BorderLayout.SOUTH);

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (!lessons.isEmpty()) {
            updateContent();
        } else {
            titleLabel.setText("No lessons available.");
            contentPane.setText("");
            imageLabel.setIcon(null);
        }
    }

    @Override
    public void updateContent() {
        Lesson lesson = lessons.get(currentPage);
        titleLabel.setText("<html><h1>" + lesson.getTitle() + "</h1></html>");
        contentPane.setText("<html><p>" + lesson.getContent() + "</p></html>");

        // Load and display the image
        String imagePath = lesson.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image img = imageIcon.getImage();
            Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            imageLabel.setIcon(null);
        }

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < lessons.size() - 1);
    }

    @Override
    public void resetContent() {
        currentPage = 0;
        updateContent();
    }
}
