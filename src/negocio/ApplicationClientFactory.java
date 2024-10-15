/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import vista.ApplicationClientSignUpController;

/**
 *
 * @author Sergio
 */
public class ApplicationClientFactory {

    private static ApplicationClientFactory instance;
    private static Client client;

    private ApplicationClientFactory() {
    }

    public static ApplicationClientFactory getInstance() {
        if (instance == null) {
            instance = new ApplicationClientFactory();
            client = Client.getInstance();
        }
        return instance;
    }

    public void loadSignInWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/FXMLSignIN.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setTitle("Inicio de Sesión");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showErrorDialog("No se pudo cargar la vista de Inicio de Sesión.");
            e.printStackTrace();
        }
    }

    // Método para cargar la ventana de Registro (SignUp)
    public void loadSignUpWindowJavi(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLSignUp.fxml"));
            Parent root = loader.load();
            ApplicationClientSignUpController controller = (ApplicationClientSignUpController) loader.getController();
            Scene scene = new Scene(root);
            controller.setClient(client);
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException e) {
            showErrorDialog("No se pudo cargar la vista de Registro.");
            e.printStackTrace();
        }
    }

    public void loadMainWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLMain.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setTitle("Ventana Principal");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorDialog("No se pudo cargar la vista principal.");
            e.printStackTrace();
        }
    }

    // Método para cerrar una ventana
    public void closeWindow(Stage stage) {
        if (stage != null) {
            stage.close();
        }
    }

    // Método para mostrar un mensaje de error
    private void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para aplicar una hoja de estilo a la escena
    public void applyStyle(Scene scene, String cssFileName) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
    }

}
