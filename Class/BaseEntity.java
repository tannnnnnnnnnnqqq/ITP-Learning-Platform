package src.Class;

public abstract class BaseEntity {
    protected String title;
    protected String content;

    public BaseEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}