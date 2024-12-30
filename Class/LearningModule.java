package src.Class;

public class LearningModule extends BaseModule<Lesson> {
    public LearningModule() {
        super();
    }

    public void addLesson(Lesson lesson) {
        this.addItem(lesson);
    }
}

