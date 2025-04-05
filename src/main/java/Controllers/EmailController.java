package Controllers;

import AlertUser.AlertInformation;
import DatabaseConnection.DatabaseHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmailController {
    @FXML private JFXTextField emailField;
    @FXML private JFXButton subscribeButton;
    /*
    @Subscribtion handling: handling the user interaction activities through fxml file
    */
    @FXML
    public void handleSubscribe() throws SQLException {
        String recipientEmail = emailField.getText().trim();
        if(isValidEmail(recipientEmail) == false) {
            AlertInformation.showAlert("Invalid Email", "Please enter a valid email address!");
            return;
        }
        try {
            Connection databaseConnection = DatabaseHelper.getDatabaseConnection();
            final String query = "INSERT INTO Recipients (email) VALUES(?)";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, recipientEmail);
            preparedStatement.executeUpdate();
            AlertInformation.showAlert("Success", "Email added successfully!");
            emailField.clear();
        } catch (SQLException e) {
            AlertInformation.showAlert("Error", "Failed to store email.Try again!");
        }

    }

    /*
    @Validate: Validate the user emails
    */
    private static boolean isValidEmail(String recipientEmail) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        return recipientEmail.matches(emailRegex);
    }
}
