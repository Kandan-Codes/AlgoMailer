package AlertUser;

import javafx.scene.control.Alert;

public class AlertInformation {
    public static void showAlert(String status, String statusMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(status);
        alert.setHeaderText(null);
        alert.setContentText(statusMessage);
        alert.showAndWait();
    }
}
