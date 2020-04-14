import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class StoreManagerQAApp extends JFrame {
    private final QuestionProducer questionProducer;
    private JPanel applicationPanel;

    private JList sentRequestsList;
    private DefaultListModel<Question> questionListModel;

    private JList receivedRepliesList;
    private DefaultListModel<QandA> qandAListModel;

    private JLabel sentRequestsLb;
    private JLabel receivedRepliesLb;
    private JTextField questionTextField;
    private JLabel questionLb;
    private JButton askQuestionBtn;

    private AnswerConsumer answerConsumer;

    private HashMap<String, QandA> cache = new HashMap<String, QandA>();

    public StoreManagerQAApp() {
        super("Q&A - Store");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(applicationPanel);
        pack();

        answerConsumer = new AnswerConsumer(this);
        questionProducer = new QuestionProducer(this, answerConsumer);

        askQuestionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = questionTextField.getText();

                if (question != null || !question.equals("")) {
                    questionProducer.sendQuestion(question);
                }
            }
        });

        qandAListModel = new DefaultListModel<QandA>();
        receivedRepliesList.setModel(qandAListModel);

        questionListModel = new DefaultListModel<>();
        sentRequestsList.setModel(questionListModel);
    }

    public static void main(String[] args) {
        StoreManagerQAApp qa = new StoreManagerQAApp();
        qa.setVisible(true);
    }

    public void onNewAnswerReceived(String id, String content) {
        QandA qandA = cache.get(id);

        if (qandA != null) {
            qandA.setAnswer(new Answer(content));
            qandAListModel.addElement(qandA);
        }
    }

    public void onNewQuestionSent(String id, String content) {
        Question question = new Question(id, content);
        questionListModel.addElement(question);

        QandA qandA = new QandA(question);
        cache.put(id, qandA);

        questionTextField.setText("");
    }
}

