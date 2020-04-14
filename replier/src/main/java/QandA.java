public class QandA {
    private Question question;
    private Answer answer;

    public QandA(Question question) {
        this.question = question;
    }

    public String getId() {
        return question.getId();
    }

    public String getQuestion() {
        return question.getContent();
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        String result = question + "\n";

        if (answer != null) {
            result += answer;
        }

        return result;
    }
}
