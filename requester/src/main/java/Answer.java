public class Answer {
    private String content;

    public Answer(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "A: " + getContent();
    }
}
