package src.Class;

import src.Interface.Identifiable;

public class Lesson extends BaseEntity implements Identifiable {
    
    private String imagePath;

    public Lesson(String title, String content, String imagePath) {
        super(title, content);
        this.imagePath = imagePath;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath; 
    }

    

    public void setContent(String content) {
        this.content = content;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}


