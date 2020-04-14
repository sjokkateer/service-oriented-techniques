import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.*;

public class AnswerProducer extends ClientBase {
    private static final String QUEUE = "answers";
    private MessageProducer producer;

    public AnswerProducer(JFrame app) {
        super(app);
    }

    public void sendAnswer(String id, String content, Destination answerDestination) {
        try {
            Answer answer = new Answer(content);
            Gson gson = new Gson();
            String jsonAnswer = gson.toJson(answer);

            Message message = session.createTextMessage(jsonAnswer);
            message.setJMSCorrelationID(id);
            producer.send(answerDestination, message);

            ((MainOfficeQAApp)app).onNewAnswerSent(message.getJMSCorrelationID(), content);
        } catch (JMSException e) {
            e.printStackTrace();
            closeConnections();
        }
    }

    @Override
    protected void doClassSpecificSetup() throws JMSException {
        destinationQueue = session.createQueue(QUEUE);
        producer = session.createProducer(null);
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
