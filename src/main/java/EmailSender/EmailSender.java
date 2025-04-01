package EmailSender;

import AlertUser.AlertInformation;
import DatabaseConnection.DatabaseHelper;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailSender {
    private static final String EMAIL = "mani535.416u@gmail.com";
    private static final String PASSWORD = System.getenv("appKey");

    public static void sendEmails() {
        List<String> recipientEmails = DatabaseHelper.getAllRecipients();
        String pattern = DatabaseHelper.getRandomPattern();
        System.out.print("My App password: " + PASSWORD);

        if(recipientEmails.isEmpty() || pattern == null) {
            AlertInformation.showAlert("Error", "No Recipients or Patterns found!");
            return;
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        for(String recipientEmail : recipientEmails) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL));

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("Daily Programming Pattern");
                message.setContent(pattern, "text/html");
                Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
