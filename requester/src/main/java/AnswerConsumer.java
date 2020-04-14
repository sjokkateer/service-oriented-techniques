import com.google.gson.Gson;
import javax.jms.*;
import javax.swing.*;

public class AnswerConsumer extends ClientBase {
    private Destination destinationQueue;
    private MessageConsumer consumer;

    public AnswerConsumer(JFrame app) {
        super(app);
    }

    public Destination getDestinationQueue() {
        return destinationQueue;
    }

    @Override
    protected void doClassSpecificSetup() throws JMSException {
        destinationQueue = session.createTemporaryQueue();

        consumer = session.createConsumer(destinationQueue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    String jsonAnswer = ((TextMessage) message).getText();

                    Gson gson = new Gson();
                    Answer answer = gson.fromJson(jsonAnswer, Answer.class);

                    String id = message.getJMSCorrelationID();

                    ((StoreManagerQAApp)app).onNewAnswerReceived(id, answer.getContent());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        connection.start();
    }

    public void closeConnections() {
        try {
            if (consumer != null) {
                consumer.close();
            }

            super.closeConnections();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
