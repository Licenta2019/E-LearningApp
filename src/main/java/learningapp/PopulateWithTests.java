package learningapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.TestQuestionStatus;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.repositories.UserRepository;

//@Component
public class PopulateWithTests implements ApplicationRunner {

    private static String path = "Questions.txt";
    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Random random = new Random();
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(path)));
            Scanner ss;
            s.useDelimiter("#");

            Optional<Topic> javat = topicRepository.
                    findById(UUID.fromString("15ba3454-65e2-439c-8519-9ba135cf97ba"));

            Topic java = javat.get();
            Topic csharp = topicRepository.findById(UUID.fromString("15ba3454-65e2-439c-8519-9ba135cf97bb")).get();
            User paul = userRepository.findByUsername("paul").get();
            while (s.hasNext()) {
                String info = s.next();
                ss = new Scanner(info);
                ss.useDelimiter("[|]");
                String text = ss.next();

                TestQuestion testQuestion = new TestQuestion();
                testQuestion.setTopic(random.nextInt()%2 == 0 ? java : csharp);
                testQuestion.setStatus(TestQuestionStatus.VALIDATED);
                testQuestion.setDifficulty(random.nextInt(9) + 1);
                testQuestion.setText(text);
                testQuestion.setAuthor(paul);

                String cuv;

                List<TestAnswer> answerList = new ArrayList<>();
                for (int i = 0; i < 4; ++i) {
                    cuv = ss.next();

                    TestAnswer testAnswer = new TestAnswer();
                    testAnswer.setCorrect(cuv.endsWith("t"));
                    testAnswer.setText(cuv.substring(0, cuv.length() - 1));
                    answerList.add(testAnswer);
                }

                testQuestion.setExplanation(s.hasNext() ? s.next() : "explicatie:");
                testQuestionRepository.save(testQuestion);

                answerList.forEach(answer -> answer.setQuestion(testQuestion));

                testAnswerRepository.saveAll(answerList);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
