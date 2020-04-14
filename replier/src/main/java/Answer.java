public class Answer {
    private String content;

    public Answer(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "A: " + content;
    }
}
