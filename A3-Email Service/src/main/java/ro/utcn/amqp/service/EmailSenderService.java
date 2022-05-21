package ro.utcn.amqp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ro.utcn.amqp.entity.Email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailSenderService {

    private static final Logger logger = Logger.getLogger(EmailSenderService.class.getName());

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(Email mail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");

        Context context = new Context();
        context.setVariables(mail.getTemplateProps());
        String htmlBody = templateEngine.process(mail.getTemplatePath(), context);

        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(htmlBody, true);

        emailSender.send(message);

        logger.log(Level.INFO, "Email to " + mail.getTo() + " was sent successfully ");
    }

}
