package br.com.alx.scrapper.service.impl.email;

import br.com.alx.scrapper.exception.EmailNotSentException;
import br.com.alx.scrapper.scrap.PageScrapperComponent;
import br.com.alx.scrapper.service.email.EmailService;
import br.com.alx.scrapper.utils.CompareTextDifferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class EmailServiceImpl implements EmailService {

    private final String emailAddress;
    private final String emailSubject;
    private final String emailText;

    private final JavaMailSender emailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    public EmailServiceImpl(
            @Value("${spring.mail.username}") String emailAddress,
            JavaMailSender emailSender) {
        this.emailAddress = emailAddress;
        this.emailSender = emailSender;


        var locale = Locale.getDefault();
        var messages = ResourceBundle.getBundle("messages", locale);

        this.emailSubject = messages.getString("email.subject");
        this.emailText = messages.getString("email.content");
    }

    @Override
    public void sendSimpleMessage(String storeName, String oldContent, String newContent) throws EmailNotSentException {
        ZonedDateTime dateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        MimeMessage message = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(this.emailAddress);
            helper.setTo(this.emailAddress);
            helper.setSubject(MessageFormat.format(this.emailSubject, storeName, dateTime.format(formatter)));

            List<String> contentDifferences = CompareTextDifferences.generateHtmlHighlightDifferences(oldContent, newContent);
            helper.setText(MessageFormat.format(this.emailText, storeName, contentDifferences.get(0), contentDifferences.get(1)), true);
            this.emailSender.send(message);

            LOGGER.info("Email sent successfully.");
        } catch (MessagingException e) {
            throw new EmailNotSentException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
