import com.google.gson.Gson;
import javax.jms.*;
import javax.swing.*;

public class QuestionProducer extends ClientBase {
    private static final String QUEUE = "questions";
    private final AnswerConsumer answerConsumer;
    private MessageProducer producer;

    public QuestionProducer(JFrame app, AnswerConsumer answerConsumer) {
        super(app);
        this.answerConsumer = answerConsumer;
    }

    public void sendQuestion(String question) {
        try {
            Question q = new Question(question);
            Gson gson = new Gson();
            String jsonQuestion = gson.toJson(q);

            Message message = session.createTextMessage(jsonQuestion);
            message.setJMSReplyTo(answerConsumer.getDestinationQueue());

            producer.send(message);

            ((StoreManagerQAApp)app).onNewQuestionSent(message.getJMSMessageID(), question);
        } catch (JMSException e) {
            e.printStackTrace();
            closeConnections();
        }
    }

    @Override
    protected void doClassSpecificSetup() throws JMSException {
        destinationQueue = session.createQueue(QUEUE);
        producer = session.createProducer(destinationQueue);
    }

    public void closeConnections() {
        try {
            if (producer != null) {
                producer.close();
            }

            super.closeConnections();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
