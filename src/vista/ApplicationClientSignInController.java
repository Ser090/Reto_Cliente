/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import negocio.ApplicationClientFactory;
import negocio.Client;

/**
 *
 * @author 2dam
 */
public class ApplicationClientSignInController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientSignInController.class.getName());
    private Stage stage = new Stage();
    private ApplicationClientFactory factory = ApplicationClientFactory.getInstance();
    private Client client;

    @FXML
    private Label label;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        try {
            LOGGER.info("Inicializando la carga del stage");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);
            //btnRegistrar.addEventHandler(ActionEvent.ACTION, this::handleButtonRegistrar);
            //  scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el stage", e);
        }
    }

    public void setLogin(String login) {
        if (login != null && login != "") {
            loginField.setText(login);
            passwordField.requestFocus();
        }
    }

    private void handleWindowShowing(javafx.event.Event event) {
        LOGGER.info("Mostrando Ventana de Inicio de Sesión");
    }

}
