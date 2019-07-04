package learningapp.handlers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import learningapp.entities.Test;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PdfExporter {

    public static String exportToPdf(Test test) throws FileNotFoundException, DocumentException {
        Document document = new Document();

        String path = "F:\\LearningAppTests\\" + test.getName() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();
        document.addTitle(test.getName());

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        int questionIndex = 1;
        for (TestQuestion testQuestion : test.getQuestions()) {
            addQuestion(document, font, testQuestion, questionIndex++);
        }
        document.close();

        log.info("exported" + path);

        return  test.getName() + ".pdf";
    }

    private static void addQuestion(Document document, Font font, TestQuestion question, int number) throws DocumentException {
        Paragraph paragraph = new Paragraph();

        paragraph.setFont(font);

        paragraph.add(number + ". " + question.getText() + "\n");

        char answerIndex = 'a';
        for (TestAnswer testAnswer : question.getAnswers()) {
            paragraph.add(answerIndex + ". " + testAnswer.getText() + "\n");
            answerIndex += 1;
        }

        paragraph.add("\n\n");

        document.add(paragraph);
    }

}
