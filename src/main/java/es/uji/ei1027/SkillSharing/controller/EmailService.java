package es.uji.ei1027.SkillSharing.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }
    public void sendEmail(String toEmail, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("TUSMUERTOSCAGAOS>:c@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        this.mailSender.send(message);
    }
}
