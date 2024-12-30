package src.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.Class.Lesson;
import src.util.FileUtils;

public class LessonDAO {
    private static final String FILE_PATH = "src/Dao/Lesson.txt";

    public void saveLesson(Lesson lesson) {
        List<String> lines = FileUtils.readFile(FILE_PATH);
        lines.add(lesson.getTitle() + "|" + lesson.getContent() + "|" + lesson.getImagePath());
        FileUtils.writeFile(FILE_PATH, lines);
    }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    lessons.add(new Lesson(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lessons;
    }
}

