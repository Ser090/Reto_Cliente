package view;

import business.ApplicationClientFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utilidades.Signable;

public class FXMLPasswordChangeController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    // Método para manejar el restablecimiento de la contraseña
    @FXML
    private void handleButtonChangePassword() {
        String email = emailField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Por favor, ingrese su correo y las contraseñas.");
        } else if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
        } else {
            // Obtener la instancia del cliente usando la factoría
            Signable client = ApplicationClientFactory.getInstance().access();
            
            // Verificar si el correo existe en la base de datos
            if (client.verifyEmail(email)) {
                // Si el correo existe, actualizar la contraseña
                if (client.updatePassword(email, newPassword)) {
                    showAlert("Éxito", "Contraseña restablecida con éxito.");
                } else {
                    showAlert("Error", "No se pudo actualizar la contraseña.");
                }
            } else {
                // Si el correo no existe, mostrar un error
                showAlert("Error", "El correo no está registrado.");
            }
        }
    }

    // Método para mostrar alertas
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
