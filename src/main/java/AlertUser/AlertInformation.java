package AlertUser;

import javafx.scene.control.Alert;
/*
@Alerting: provide alert message when specific things happen
*/
public class AlertInformation {
    public static void showAlert(String status, String statusMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(status);
        alert.setHeaderText(null);
        alert.setContentText(statusMessage);
        alert.showAndWait();
    }
}
