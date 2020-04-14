import com.google.gson.Gson;

import javax.jms.*;
import javax.swing.*;

public class QuestionConsumer extends ClientBase {
    private static final String QUEUE = "questions";
    private MessageConsumer consumer;

    public QuestionConsumer(JFrame app) {
        super(app);
    }

    @Override
    protected void doClassSpecificSetup() throws JMSException {
        destinationQueue = session.createQueue(QUEUE);
        consumer = session.createConsumer(destinationQueue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    String id = message.getJMSMessageID();
                    String jsonQuestion = ((TextMessage) message).getText();

                    Gson gson = new Gson();
                    Question question = gson.fromJson(jsonQuestion, Question.class);

                    ((MainOfficeQAApp)app).onNewQuestionReceived(id, question.getContent(), message.getJMSReplyTo());
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
