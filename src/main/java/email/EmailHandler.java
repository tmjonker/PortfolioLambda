package email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class EmailHandler {

    Logger logger = LoggerFactory.getLogger(EmailHandler.class);
    private final String TO = "tmjonker1@outlook.com";
    private final String FROM = "tmjonker.email.service@gmail.com";
    private final String HOST = "smtp.gmail.com";
    private final String PORT = "587";
    private final String SUBJECT = "LAST WEEK'S MESSAGES";

    private String message, password, username;
    private Date date = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("E yyyy.MM.dd HH:mm:SS");

    public EmailHandler() {
        password = loadPassword();
        username = "tmjonker.email.service";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String loadPassword() {
        String password = "";
        try  {
            InputStream in = getClass().getResourceAsStream("/email_credentials.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            password = reader.readLine();
            System.out.println(password);
        } catch (Exception e) {
            logger.error("Exception --> Class EmailHandler --> error in method loadPassword(): " + e.getMessage());
        }
        return password;
    }

    public void generateMessage() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            message.setSubject(SUBJECT);
            message.setText(this.message);

            Transport.send(message);
        } catch (Exception e) {
            logger.error("Exception --> class EmailHandler --> method generateMessage() --> " + e.getMessage());
        }
    }
}
