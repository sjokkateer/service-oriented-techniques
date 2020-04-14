import javax.jms.Destination;

public class Question {
    private Destination answerAddress;
    private String id;
    private String content;

    public Question() {

    }

    public Question(String content) {
        this.content = content;
    }

    public Question(String id, String content) {
        this(content);
        this.id = id;
    }

    public Question(String id, String content, Destination answerAddress) {
        this(id, content);
        this.answerAddress = answerAddress;
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

    public Destination getAnswerAddress() {
        return answerAddress;
    }

    public void setAnswerAddress(Destination answerAddress) {
        this.answerAddress = answerAddress;
    }

    @Override
    public String toString() {
        return "Q: " + content;
    }
}
