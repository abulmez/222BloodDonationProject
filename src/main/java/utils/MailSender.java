package utils;

import javafx.concurrent.Task;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender extends Task {

    private Session session;
    private String destination,subject,content;


    public MailSender(String destination,String subject,String content) {

        final String username = "donatii.sange@gmail.com";
        final String password = "sange1357";
        this.destination=destination;
        this.subject=subject;
        this.content=content;

        Properties props = new Properties();
        props.put("mail.smtp.user", "donatii.sange@gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", 587);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    @Override
    protected Object call() throws Exception {
        sendMail();
        return null;
    }



    public void sendMail() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("donatii.sange@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destination));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}