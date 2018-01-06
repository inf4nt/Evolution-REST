package evolution.service.notification;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(MailService.class);
    private static final String FROM = "notification.evolution@gmail.com";

    public void send(String to, String subject, String message) {

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, true);
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            logger.info("Message send:= " + mimeMessageHelper.toString());
            javaMailSender.send(mail);
        } catch (MessagingException exception) {
            logger.info("error", exception);
        }
    }
}
