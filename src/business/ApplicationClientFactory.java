/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utilidades.User;
import view.ApplicationClientMainUserController;
import view.ApplicationClientSignInController;
import view.ApplicationClientSignUpController;

/**
 *
 * @author Sergio
 */
public class ApplicationClientFactory {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientFactory.class.getName());
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

    public void loadSignInWindow(Stage stage, String login) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLSignIn.fxml"));
            Parent root = loader.load();
            ApplicationClientSignInController controller = (ApplicationClientSignInController) loader.getController();
            controller.setClient(client);
            controller.setStage(stage);
            controller.setLogin(login);
            controller.initStage(root);
        } catch (IOException e) {
            LOGGER.severe("Abriendo ventana SignIn: " + e.getMessage());
            showErrorDialog("No se pudo cargar la vista de Inicio de Sesion.");

        }
    }

    // Método para cargar la ventana de Registro (SignUp)
    public void loadSignUpWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLSignUp.fxml"));
            Parent root = loader.load();
            ApplicationClientSignUpController controller = (ApplicationClientSignUpController) loader.getController();
            controller.setClient(client);
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException e) {
            LOGGER.severe("Abriendo ventana SignUp: " + e.getMessage());
            showErrorDialog("No se pudo cargar la vista de Registro.");

        }
    }

    public void loadMainUserWindow(Stage stage, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLMain.fxml"));
            Parent root = loader.load();
            ApplicationClientMainUserController controller = (ApplicationClientMainUserController) loader.getController();
            controller.setClient(client);
            controller.setUser(user);
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException e) {
            LOGGER.severe("Abriendo ventana Main: " + e.getMessage());
            showErrorDialog("No se pudo cargar la vista principal.");

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
