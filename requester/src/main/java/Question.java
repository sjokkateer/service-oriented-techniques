public class Question {
    private String id;
    private String content;

    public Question(String content) {
        this.content = content;
    }

    public Question(String id, String content) {
        this(content);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Q: " + content;
    }
}
