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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import negocio.ApplicationClientFactory;
import negocio.Client;
import utilidades.Message;
import utilidades.User;

/**
 *
 * @author 2dam
 */
public class ApplicationClientSignInController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientSignInController.class.getName());
    private Stage stage = new Stage();
    private ApplicationClientFactory factory = ApplicationClientFactory.getInstance();
    private Client client;
    private boolean hasError = false;
    private User user;

    @FXML
    private Label label;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerLink;
    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView errorImageLogin;
    @FXML
    private ImageView errorImagePass;

    private final String EMAILPATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String PASSPATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9]).{9,}$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Añadir listener a cada TextField o PasswordField en el GridPane
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField || node instanceof PasswordField) {
                node.setOnKeyTyped(event -> hideErrorImage(node));  // Ocultar error tan pronto como se escribe algo
            }
        }
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
            loginButton.setOnAction(null);
            loginButton.addEventHandler(ActionEvent.ACTION, this::handleButtonLoginButton);
            registerLink.setOnAction(this::handleHyperLinkRegistry);
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

    @FXML
    private void handleHyperLinkRegistry(ActionEvent event) {
        ApplicationClientFactory.getInstance().loadSignUpWindowJavi(stage);
    }

    @FXML
    private void handleButtonLoginButton(ActionEvent event) {
        LOGGER.info("Botón Aceptar presionado");

        // Verificar si todos los campos están llenos
        if (!areAllFieldsFilled()) {
            LOGGER.severe("Error: Todos los campos deben ser completados.");
            showErrorDialog("Todos los campos deben ser rellenados.");
            for (Node node : gridPane.getChildren()) {
                if (node instanceof TextField || node instanceof PasswordField) {
                    if (((TextField) node).getText().isEmpty()) {
                        showErrorImage(node); // Mostrar error y marcar el campo
                        hasError = true;
                    }
                }
            }
        } else {
            // Validar campos específicos como contraseña y correo electrónico
            if (!isValid(passwordField.getText(), PASSPATTERN)) {
                showErrorImage(passwordField);
                hasError = true;
            }

            if (!isValid(loginField.getText(), EMAILPATTERN)) {
                showErrorImage(loginField);
                hasError = true;
            }
        }
        // Si hay errores, no continuar
        if (hasError) {
            LOGGER.severe("Hay errores en el formulario.");
            // Volver a habilitar el botón si hay errores
            loginButton.setDisable(false);
            return;
        } else {
            // Si no hay errores, proceder con el registro
            user = new User();
            user.setLogin(loginField.getText());
            user.setPass(passwordField.getText());

            LOGGER.info("Validación de campos correcta.");
            Message response = client.signUp(user);
            messageManager(response);
        }

    }

    private void messageManager(Message message) {
        switch (message.getType()) {
            case OK_RESPONSE:
                // Aviso de registro correcto y vuelta a la ventana de sign in
                loginButton.setDisable(true);
                new Alert(Alert.AlertType.CONFIRMATION, "El registro se ha realizado con éxito.").showAndWait();
                factory.loadSignInWindow(stage, user.getLogin());

                break;
            case SIGNUP_ERROR:
                // Se ha producido un error al registrar al usuario
                break;
            case LOGIN_EXIST_ERROR:
                // El login está repetido, avisar al usuario
                loginField.setStyle("-fx-border-color: red;");
                errorImageLogin.setVisible(true);
                break;
            case BAD_RESPONSE:
                // Se ha producido un error al registrar al usuario
                break;
            case SQL_ERROR:
                // El servidor no está operativo, hable con sistemas
                break;
        }
    }

    private boolean areAllFieldsFilled() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField || node instanceof PasswordField) {
                if (((TextField) node).getText() == null || ((TextField) node).getText().isEmpty()) {
                    LOGGER.severe("Error: El campo " + ((TextField) node).getPromptText() + " está vacío.");
                    return false;
                }
            }
        }
        return true;
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Boolean isValid(String validacion, String VALIDATOR) {
        Pattern patron = Pattern.compile(VALIDATOR);
        Matcher matcher = patron.matcher(validacion);
        return matcher.matches();
    }

    private void showErrorImage(Node node) {

        node.getStyleClass().add("error-field");  // Añadir clase CSS para marcar el error
        showErrorIcon(node);  // Mostrar icono de error

    }

    private void hideErrorImage(Node node) {
        node.getStyleClass().remove("error-field");  // Eliminar clase CSS
        hideErrorIcon(node);  // Ocultar el icono de error

    }

    private void showErrorIcon(Node node) {
        if (node == loginField) {
            errorImageLogin.setVisible(true);
        } else if (node == passwordField) {
            errorImagePass.setVisible(true);
        }
    }

    private void hideErrorIcon(Node node) {
        if (node == loginField) {
            errorImageLogin.setVisible(false);
        } else if (node == passwordField) {
            errorImagePass.setVisible(false);
        }
    }
}
