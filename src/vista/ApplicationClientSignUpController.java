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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import negocio.Client;
import utilidades.Message;
import utilidades.MessageType;
import utilidades.User;

/**
 * FXML Controller class for the SignUp view.
 */
public class ApplicationClientSignUpController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientSignUpController.class.getName());
    private Stage stage = new Stage();

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
    private Button btAceptar;
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

    private Client client;

    private final String EMAILPATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String PASSPATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9]).{9,}$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ocultar todas las imágenes de error inicialmente
        errorImageName.setVisible(false);
        errorImageSurname1.setVisible(false);
        errorImageSurname2.setVisible(false);
        errorImageEmail.setVisible(false);
        errorImagePass.setVisible(false);
        errorImagePassRepeat.setVisible(false);
        errorImageStreet.setVisible(false);
        errorImageCity.setVisible(false);
        errorImageZip.setVisible(false);

        // Añadir listener a cada TextField o PasswordField en el GridPane
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    hideErrorImage(textField);  // Ocultar la imagen de error y resetear el estilo
                });
            } else if (node instanceof PasswordField) {
                PasswordField passwordField = (PasswordField) node;
                passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
                    hideErrorImage(passwordField);  // Ocultar la imagen de error y resetear el estilo
                });
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
            stage.setTitle("Registro de Usuario");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);
            btAceptar.addEventHandler(ActionEvent.ACTION, this::handleButtonAceptarAction);
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el stage", e);
        }
    }

    private void handleWindowShowing(javafx.event.Event event) {
        LOGGER.info("Mostrando Ventana de registro");
    }

    private void hideErrorImage(Node node) {
        // Restablecer el estilo del campo
        node.setStyle("");

        // Ocultar la imagen de error correspondiente
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

    @FXML
    private void handleButtonAceptarAction(ActionEvent event) {
        LOGGER.info("Botón Aceptar presionado");

        // Llamar al método para comprobar que todos los campos están llenos
        if (!areAllFieldsFilled()) {
            LOGGER.severe("Error: Todos los campos deben ser completados.");
            return;
        }

        boolean hasError = false;

        // Validar todos los campos
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText().isEmpty()) {
                    showErrorImage(textField); // Mostrar error y marcar el campo
                    hasError = true;
                }
            } else if (node instanceof PasswordField) {
                PasswordField passwordField = (PasswordField) node;
                if (passwordField.getText().isEmpty()) {
                    showErrorImage(passwordField); // Mostrar error y marcar el campo
                    hasError = true;
                }
            }
        }

        // Verificar validaciones específicas (contraseña, email, etc.)
        if (!isValid(passwordField.getText(), PASSPATTERN)) {
            passwordField.setStyle("-fx-border-color: red;");
            errorImagePass.setVisible(true);
            hasError = true;
        }

        if (!passwordField.getText().equals(confirmpasswordField.getText())) {
            confirmpasswordField.setStyle("-fx-border-color: red;");
            errorImagePassRepeat.setVisible(true);
            hasError = true;
        }

        if (!isValid(emailField.getText(), EMAILPATTERN)) {
            emailField.setStyle("-fx-border-color: red;");
            errorImageEmail.setVisible(true);
            hasError = true;
        }

        // Si hay errores, no continuar
        if (hasError) {
            LOGGER.severe("Hay errores en el formulario.");
            return;
        }

        // Si no hay errores, proceder con el registro
        User user = new User(emailField.getText(), passwordField.getText(),
                nameField.getText() + " " + surname1Field.getText() + " " + surname2Field.getText(),
                streetField.getText(), zipField.getText(), cityField.getText(), activeCheckBox.isSelected());

        LOGGER.info("Validación de campos correcta.");
        messageManager(client.signUp(user));
    }

    private void showErrorImage(Node node) {
        // Marcar el campo con borde rojo y mostrar la imagen de error correspondiente
        node.setStyle("-fx-border-color: red;");

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

    private void messageManager(Message message) {
        switch (message.getType()) {
            case OK_RESPONSE:
                // Aviso de registro correcto y vuelta a la ventana de sign in
                break;
            case SIGNUP_ERROR:
                // Se aproducido un error al registrar al usuario
                break;
            case LOGIN_EXIST_ERROR:
                // El login está repetido, avisar al usuario
                emailField.setStyle("-fx-border-color: red;");
                errorImageEmail.setVisible(true);
                break;
            case BAD_RESPONSE:
                // Se aproducido un error al registrar al usuario
                break;
            case SQL_ERROR:
                // El servidor no está operativo, hable con sistemas
                break;
        }
    }

    /**
     * Valida si todos los campos del formulario están llenos.
     *
     * @return true si todos los campos están llenos, false en caso contrario.
     */
    private boolean areAllFieldsFilled() {
        // Recorre todos los nodos hijos del GridPane
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText() == null || textField.getText().isEmpty()) {
                    LOGGER.severe("Error: El campo " + textField.getPromptText() + " está vacío.");
                    return false;
                }
            } else if (node instanceof PasswordField) {
                PasswordField passwordField = (PasswordField) node;
                if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
                    LOGGER.severe("Error: El campo " + passwordField.getPromptText() + " está vacío.");
                    return false;
                }
            }
        }
        return true;  // Todos los campos están llenos
    }

    /**
     * Valida si una cadena cumple con el patrón proporcionado.
     *
     * @param validacion El texto a validar.
     * @param VALIDATOR El patrón con el que validar.
     * @return true si la cadena cumple con el patrón, false en caso contrario.
     */
    private Boolean isValid(String validacion, String VALIDATOR) {
        Pattern patron = Pattern.compile(VALIDATOR);
        Matcher matcher = patron.matcher(validacion);
        return matcher.matches();
    }
}
