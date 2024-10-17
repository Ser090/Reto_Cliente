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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import negocio.ApplicationClientFactory;
import negocio.Client;
import utilidades.Message;
import utilidades.User;

/**
 * FXML Controller class for the SignUp view.
 */
public class ApplicationClientSignUpController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientSignUpController.class.getName());
    private Stage stage = new Stage();
    private ApplicationClientFactory factory = ApplicationClientFactory.getInstance();
    private User user;
    private boolean hasError = false;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surname1Field;
    @FXML
    private TextField surname2Field;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmpasswordField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField zipField;
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private GridPane gridPane;  // Asume que todos los campos están dentro de este GridPane
    @FXML
    private ImageView errorImageName;
    @FXML
    private ImageView errorImageSurname1;
    @FXML
    private ImageView errorImageSurname2;
    @FXML
    private ImageView errorImageEmail;
    @FXML
    private ImageView errorImagePass;
    @FXML
    private ImageView errorImagePassRepeat;
    @FXML
    private ImageView errorImageStreet;
    @FXML
    private ImageView errorImageCity;
    @FXML
    private ImageView errorImageZip;
    @FXML
    private HBox warningbox;

    private Client client;

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
            scene.focusOwnerProperty();
            stage.setScene(scene);
            stage.setTitle("Registro de Usuario");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);
            btnRegistrar.setOnAction(null);  // Eliminar cualquier manejador anterior
            /*Si alguna parte del código está corriendo en un hilo separado
            (por ejemplo, si llamas a un servicio remoto o una tarea asíncrona),
            asegúrate de que no estés haciendo la misma llamada varias veces de manera simultánea.*/
            btnRegistrar.addEventHandler(ActionEvent.ACTION, this::handleButtonRegistrar);

            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el stage", e);
        }
    }

    private void handleWindowShowing(javafx.event.Event event) {
        LOGGER.info("Mostrando Ventana de registro");

        // Establecer el foco en el GridPane o en algún otro componente que no sea un TextField.
        gridPane.requestFocus(); // Esto evitará que el foco esté en el primer TextField.

    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleButtonRegistrar(ActionEvent event) {
        LOGGER.info("Botón Aceptar presionado");
        hasError = false;
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

            if (!passwordField.getText().equals(confirmpasswordField.getText())) {
                showErrorImage(confirmpasswordField);
                hasError = true;
            }

            if (!isValid(emailField.getText(), EMAILPATTERN)) {
                showErrorImage(emailField);
                hasError = true;
            }
        }
        // Si hay errores, no continuar
        if (hasError) {
            LOGGER.severe("Hay errores en el formulario.");
            // Volver a habilitar el botón si hay errores
            btnRegistrar.setDisable(false);
            return;
        } else {
            // Si no hay errores, proceder con el registro
            user = new User(emailField.getText(), passwordField.getText(),
                    nameField.getText() + " " + surname1Field.getText() + " " + surname2Field.getText(),
                    streetField.getText(), zipField.getText(), cityField.getText(), activeCheckBox.isSelected());

            LOGGER.info("Validación de campos correcta.");
            Message response = client.signUp(user);
            messageManager(response);
        }

    }

    @FXML
    private void handleActiveCheckBoxChange(ActionEvent event) {
        if (!activeCheckBox.isSelected()) {
            warningbox.setVisible(true);
        } else {
            warningbox.setVisible(false);
        }
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
        if (node == nameField) {
            errorImageName.setVisible(true);
        } else if (node == surname1Field) {
            errorImageSurname1.setVisible(true);
        } else if (node == surname2Field) {
            errorImageSurname2.setVisible(true);
        } else if (node == emailField) {
            errorImageEmail.setVisible(true);
        } else if (node == passwordField) {
            errorImagePass.setVisible(true);
        } else if (node == confirmpasswordField) {
            errorImagePassRepeat.setVisible(true);
        } else if (node == streetField) {
            errorImageStreet.setVisible(true);
        } else if (node == cityField) {
            errorImageCity.setVisible(true);
        } else if (node == zipField) {
            errorImageZip.setVisible(true);
        }
    }

    private void hideErrorIcon(Node node) {
        if (node == nameField) {
            errorImageName.setVisible(false);
        } else if (node == surname1Field) {
            errorImageSurname1.setVisible(false);
        } else if (node == surname2Field) {
            errorImageSurname2.setVisible(false);
        } else if (node == emailField) {
            errorImageEmail.setVisible(false);
        } else if (node == passwordField) {
            errorImagePass.setVisible(false);
        } else if (node == confirmpasswordField) {
            errorImagePassRepeat.setVisible(false);
        } else if (node == streetField) {
            errorImageStreet.setVisible(false);
        } else if (node == cityField) {
            errorImageCity.setVisible(false);
        } else if (node == zipField) {
            errorImageZip.setVisible(false);
        }
    }

    private void messageManager(Message message) {
        switch (message.getType()) {
            case OK_RESPONSE:
                // Aviso de registro correcto y vuelta a la ventana de sign in
                btnRegistrar.setDisable(true);
                new Alert(AlertType.CONFIRMATION, "El registro se ha realizado con éxito.").showAndWait();
                factory.loadSignInWindow(stage, user.getLogin());

                break;
            case SIGNUP_ERROR:
                // Se ha producido un error al registrar al usuario
                break;
            case LOGIN_EXIST_ERROR:
                // El login está repetido, avisar al usuario
                emailField.setStyle("-fx-border-color: red;");
                errorImageEmail.setVisible(true);
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

    private Boolean isValid(String validacion, String VALIDATOR) {
        Pattern patron = Pattern.compile(VALIDATOR);
        Matcher matcher = patron.matcher(validacion);
        return matcher.matches();
    }
}
