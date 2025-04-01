package DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    public static boolean addRecipient(String recipientEmail) throws SQLException {
       try(Connection databaseConnection = getDatabaseConnection()) {
           final String query = "INSERT INTO Recipients (email) VALUES(?)";
           PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
           preparedStatement.setString(1, recipientEmail);
           preparedStatement.executeUpdate();
           return true;
       } catch (Exception e) {
           return false;
       }

    }

    public static List<String> getAllRecipients() {
        List<String> recipientEmails = new ArrayList<>();
        try(Connection databaseConnection = getDatabaseConnection()) {
            final String query = "SELECT email FROM Recipients";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                recipientEmails.add(resultSet.getString("email"));
            }
            System.out.println("All Recipients: " + recipientEmails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipientEmails;
    }

    public static String getRandomPattern() {
        String pattern = "";
        try(Connection databaseConnection = getDatabaseConnection()) {
            final String query = "SELECT pattern_title, pattern_text, pattern_code FROM Patterns ORDER BY RAND() LIMIT 1";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                pattern += generateEmailHTMLContent(resultSet.getString("pattern_title"), resultSet.getString("pattern_text"), resultSet.getString("pattern_code"));
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
        return pattern;
    }

    private static String generateEmailHTMLContent(String pattern_title, String pattern_text, String pattern_code) {
        return "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #4CAF50;'> Programming Pattern </h2>" +
                "<h3>" + pattern_title + "</h3>" +
                "<pre style='background: #f4f4f4; padding: 10px; border-left: 3px solid #4CAF50;'>" +
                pattern_text + "</pre>" +
                "<pre style='background: #f4f4f4; padding: 10px; border-left: 3px solid #4CAF50;'>" +
                pattern_code + "</pre>" +
                "<p> Happy Coding! </p>" +
                "</body>" +
                "</html>";

    }

    public static Connection getDatabaseConnection() throws SQLException {
        final String URL = "jdbc:mysql://localhost:3306/EmailSender";
        final String USERNAME = "root";
        final String PASSWORD = "@@mani143@@";
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
