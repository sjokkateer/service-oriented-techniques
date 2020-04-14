import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.*;

abstract public class ClientBase {
    private static final String BROKER_URL = "tcp://localhost:61616";

    protected final JFrame app;
    protected Connection connection;
    protected Session session;
    protected Destination destinationQueue;

    public ClientBase(JFrame app) {
        this.app = app;
        ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);

        try {
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            doClassSpecificSetup();
        } catch (JMSException e) {
            e.printStackTrace();
            closeConnections();
        }
    }

    protected abstract void doClassSpecificSetup() throws JMSException;

    public void closeConnections() {
        try {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
