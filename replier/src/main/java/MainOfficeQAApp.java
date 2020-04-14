import javax.jms.Destination;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MainOfficeQAApp extends JFrame {
    private final AnswerProducer answerProducer;
    private JPanel applicationPanel;

    private JList sentRepliesList;
    private DefaultListModel<QandA> qandAListModel;

    private JList receivedRequestsList;
    private DefaultListModel<Question> questionListModel;

    private JLabel sentRepliesLb;
    private JLabel receivedRequestsLb;
    private JTextField answerTextField;
    private JButton answerQuestionBtn;
    private JLabel answerLb;

    HashMap<String, QandA> cache = new HashMap<>();

    public MainOfficeQAApp() {
        super("Q&A - Main Office");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(applicationPanel);
        pack();

        questionListModel = new DefaultListModel<>();
        receivedRequestsList.setModel(questionListModel);

        qandAListModel = new DefaultListModel<>();
        sentRepliesList.setModel(qandAListModel);

        new QuestionConsumer(this);
        answerProducer = new AnswerProducer(this);

        answerQuestionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = answerTextField.getText();

                Object selectedObject = receivedRequestsList.getSelectedValue();

                if (answer != null && !answer.equals("") && selectedObject != null) {
                    Question q = (Question) selectedObject;
                    answerProducer.sendAnswer(q.getId(), answer, q.getAnswerAddress());
                }
            }
        });
    }

    public static void main(String[] args) {
        MainOfficeQAApp mainOfficeQAApp = new MainOfficeQAApp();
        mainOfficeQAApp.setVisible(true);
    }

    public void onNewQuestionReceived(String id, String content, Destination answerAddress) {
        Question question = new Question(id, content, answerAddress);
        questionListModel.addElement(question);

        QandA qandA = new QandA(question);
        cache.put(id, qandA);
    }

    public void onNewAnswerSent(String id, String content) {
        QandA qandA = cache.get(id);
        qandA.setAnswer(new Answer(content));
        qandAListModel.addElement(qandA);

        resolveQuestion();
    }

    private void resolveQuestion() {
        int selectedIndex = receivedRequestsList.getSelectedIndex();
        questionListModel.remove(selectedIndex);
        answerTextField.setText("");
    }
}
