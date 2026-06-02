package com.roofiahmad.springstoreapp.common;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    public void sendOrderEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException {
        Context context = new Context();
        context.setVariables(templateModel);

        String htmlContent = templateEngine.process("order-confirmation", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("no-reply@roofiahmad-homelabs.my.id");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        System.out.println("Sending email...");
        mailSender.send(message);
        System.out.println("Email sent!");
    }

}
