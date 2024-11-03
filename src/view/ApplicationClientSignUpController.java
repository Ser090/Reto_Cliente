package view;

import business.ApplicationClientFactory;
import business.Client;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utilidades.Message;
import utilidades.User;
import static utilities.AlertUtilities.showErrorDialog;
import static utilities.ValidateUtilities.isValid;

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
    private Label labelTitulo;
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
    private TextField passwordFieldVisual;
    @FXML
    private TextField confirmPasswordFieldVisual;
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
    @FXML
    private Button toggleVisibilityButton1;
    @FXML
    private Button toggleVisibilityButton2;

    private ContextMenu contextMenu;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Crear el menú contextual personalizado
        contextMenu = new ContextMenu();

        // Añadir una clase de estilo para el menú contextual
        contextMenu.getStyleClass().add("context-menu");
        // Aplicar el mismo estilo que el Tooltip al ContextMenu
        contextMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);"
                + "-fx-text-fill: #FFFFFF;"
                + "-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Protest Strike';"
                + "-fx-max-width: 250px;"
                + "-fx-wrap-text: true;"
                + "-fx-padding: 10px;"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 5;"
                + "-fx-background-radius: 5;");
        // Opción "Borrar campos"
        MenuItem clearFieldsItem = new MenuItem("Borrar campos");
        clearFieldsItem.setStyle("-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Protest Strike';"
                + "-fx-text-fill: #FFFFFF;"
                + "-fx-background-color: transparent;"
                + "-fx-max-width: 250px;"
                + "-fx-wrap-text: true;");
        clearFieldsItem.setOnAction(event -> handleClearFields());

        // Opción "Salir"
        MenuItem exitItem = new MenuItem("Salir");
        exitItem.setStyle("-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Protest Strike';"
                + "-fx-text-fill: #FFFFFF;"
                + "-fx-background-color: transparent;"
                + "-fx-max-width: 250px;"
                + "-fx-wrap-text: true;");
        exitItem.setOnAction(event -> handleExit());

        // Añadir las opciones personalizadas al menú contextual
        contextMenu.getItems().addAll(clearFieldsItem, exitItem);

        // Asignar el menú personalizado a cada campo de texto y eliminar el menú predeterminado
        assignCustomContextMenu(nameField);
        assignCustomContextMenu(surname1Field);
        assignCustomContextMenu(surname2Field);
        assignCustomContextMenu(emailField);
        assignCustomContextMenu(streetField);
        assignCustomContextMenu(cityField);
        assignCustomContextMenu(zipField);
        assignCustomContextMenu(passwordField);
        assignCustomContextMenu(confirmpasswordField);

        // Asignar el menú contextual al GridPane
        gridPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(gridPane, event.getScreenX(), event.getScreenY());
            }
        });

        // Añadir listener a cada TextField o PasswordField en el GridPane
        for (Node node : gridPane.getChildren()) {
            if (node instanceof TextField || node instanceof PasswordField) {
                node.setOnKeyTyped(event -> hideErrorImage(node));  // Ocultar error tan pronto como se escribe algo
            }
        }

        // Asegura que se salte estos campos porque son auxiliares
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                confirmpasswordField.requestFocus();
            }
        });

        confirmpasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                streetField.requestFocus();
            }
        });
    }

    private void assignCustomContextMenu(TextField textField) {
        // Asignar el menú contextual personalizado y eliminar el predeterminado
        textField.setContextMenu(contextMenu);
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
            btnRegistrar.setOnAction(null);
            btnCancelar.setOnAction(null);// Eliminar cualquier manejador anterior
            /*Si alguna parte del código está corriendo en un hilo separado
            (por ejemplo, si llamas a un servicio remoto o una tarea asíncrona),
            asegúrate de que no estés haciendo la misma llamada varias veces de manera simultánea.*/
            btnRegistrar.addEventHandler(ActionEvent.ACTION, this::handleButtonRegister);
            btnCancelar.addEventHandler(ActionEvent.ACTION, this::handleButtonCancel);
            activeCheckBox.addEventHandler(ActionEvent.ACTION, this::handleActiveCheckBoxChange);
            toggleVisibilityButton1.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    togglePasswordVisibility(passwordField, passwordFieldVisual);
                }
            });
            toggleVisibilityButton2.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    togglePasswordVisibility(confirmpasswordField, confirmPasswordFieldVisual);
                }
            });

            toggleVisibilityButton1.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    togglePasswordVisibilityReleased(passwordField, passwordFieldVisual);
                }
            });
            toggleVisibilityButton2.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    togglePasswordVisibilityReleased(confirmpasswordField, confirmPasswordFieldVisual);
                }
            });
            configureMnemotecnicKeys();
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el stage", e);
        }
    }

    private void configureMnemotecnicKeys() {
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isAltDown() && event.getCode() == KeyCode.C) {
                btnCancelar.fire();  // Simula el clic en el botón Cancelar
                event.consume();  // Evita la propagación adicional del evento
            } else if (event.isAltDown() && event.getCode() == KeyCode.R) {
                btnRegistrar.fire();  // Simula el clic en el botón Registrar
                event.consume();  // Evita la propagación adicional del evento
            }
        });
    }

    private void handleWindowShowing(javafx.event.Event event) {
        LOGGER.info("Mostrando Ventana de registro");

        // Establecer el foco en el GridPane o en algún otro componente que no sea un TextField.
        gridPane.requestFocus(); // Esto evitará que el foco esté en el primer TextField.

    }

    @FXML
    private void handleButtonRegister(ActionEvent event) {
        LOGGER.info("Botón Aceptar presionado");
        hasError = false;
        // Verificar si todos los campos están llenos
        if (!areAllFieldsFilled()) {
            LOGGER.severe("Error: Todos los campos deben ser completados.");
            for (Node node : gridPane.getChildren()) {
                if (node instanceof TextField || node instanceof PasswordField) {
                    if (((TextField) node).getText().isEmpty()) {
                        showErrorImage(node); // Mostrar error y marcar el campo
                        hasError = true;
                    }
                }
            }
        }
        passwordField.setText(passwordField.getText().trim());
        // Validar campos específicos como contraseña y correo electrónico
        if (!isValid(passwordField.getText(), "pass")) {
            showErrorImage(passwordField);
            hasError = true;
        }
        confirmpasswordField.setText(confirmpasswordField.getText().trim());
        if (!passwordField.getText().equals(confirmpasswordField.getText())) {
            showErrorImage(confirmpasswordField);
            hasError = true;
        }

        if (!isValid(emailField.getText(), "email")) {
            showErrorImage(emailField);
            hasError = true;
        }
        if (!isValid(zipField.getText(), "zip")) {
            showErrorImage(zipField);
            hasError = true;
        }

        // Si hay errores, no continuar
        if (hasError) {
            LOGGER.severe("Hay errores en el formulario.");
            // Volver a habilitar el botón si hay errores
            showErrorDialog(AlertType.ERROR, "Uno o varios campos incorrectos o vacios, mantenga el cursor encima de los campos para más información.");
            btnRegistrar.setDisable(false);
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
    private void handleButtonCancel(ActionEvent event) {
        // Crear la alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas cancelar?");

        // Obtener la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, realizar la acción de cancelar
            factory.loadSignInWindow(stage, "");
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
                showErrorDialog(AlertType.CONFIRMATION, "El registro se ha realizado con éxito.");
                factory.loadSignInWindow(stage, user.getLogin());
                break;
            case SIGNUP_ERROR:
                showErrorDialog(AlertType.ERROR, "Se ha producido un error al intentar registrar sus datos vuelva a intentarlo.");
                break;
            case LOGIN_EXIST_ERROR:
                showErrorDialog(AlertType.ERROR, "El correo electronico ya existe en la base de datos.");
                emailField.setStyle("-fx-border-color: red;");
                errorImageEmail.setVisible(true);
                break;
            case BAD_RESPONSE:
                showErrorDialog(AlertType.ERROR, "Error interno de la base de datos, inténtelo de nuevo...");
                break;
            case SQL_ERROR:
                showErrorDialog(AlertType.ERROR, "Error al introducir los datos en la base de datos, inténtelo de nuevo...");
                break;
            case CONNECTION_ERROR:
                showErrorDialog(AlertType.ERROR, "Error de conexion con la base de datos,  no hay conexión disponible, inténtelo de nuevo...");
                break;
            case SERVER_ERROR:
                showErrorDialog(Alert.AlertType.ERROR, "Servidor no encontrado, inténtelo de nuevo...");
                break;
        }
    }

    private boolean areAllFieldsFilled() {
        for (Node node : gridPane.getChildren()) {
            if ((node instanceof TextField || node instanceof PasswordField) && (node != passwordFieldVisual) && (node != confirmPasswordFieldVisual)) {
                if (((TextField) node).getText() == null || ((TextField) node).getText().isEmpty()) {
                    LOGGER.severe("Error: El campo " + ((TextField) node).getPromptText() + " está vacío.");

                    return false;
                }
            }
        }
        return true;
    }

    @FXML
    private void handleClearFields() {
        nameField.clear();
        surname1Field.clear();
        surname2Field.clear();
        emailField.clear();
        passwordField.clear();
        confirmpasswordField.clear();
        streetField.clear();
        cityField.clear();
        zipField.clear();
        labelTitulo.requestFocus();
    }

    @FXML
    private void handleExit() {
        // Obtener el Stage a través del GridPane (o cualquier otro nodo de la ventana)
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();  // Cierra la ventana
    }

    private void togglePasswordVisibility(PasswordField passwordFieldParam, TextField textFieldParam) {

        // Mostrar el TextField y ocultar el PasswordField
        textFieldParam.setText(passwordFieldParam.getText());  // Copiar contenido del PasswordField al TextField
        passwordFieldParam.setVisible(false);
        textFieldParam.setVisible(true);

        // Cambiar la imagen del botón a "mostrar"
        if (passwordFieldParam == passwordField) {
            ImageView imageView = (ImageView) toggleVisibilityButton1.getGraphic();
            imageView.setImage(new Image("resources/iconos/ocultar.png"));
        } else if (passwordFieldParam == confirmpasswordField) {
            ImageView imageView = (ImageView) toggleVisibilityButton2.getGraphic();
            imageView.setImage(new Image("resources/iconos/ocultar.png"));
        }
        // Recuperar el foco y colocar el cursor al final del texto sin seleccionar todo
        textFieldParam.requestFocus();
        textFieldParam.positionCaret(textFieldParam.getText().length());
    }

    private void togglePasswordVisibilityReleased(PasswordField passwordFieldParam, TextField textFieldParam) {

        // Mostrar el PasswordField y ocultar el TextField
        passwordFieldParam.setText(textFieldParam.getText());  // Copiar contenido del TextField al PasswordField
        passwordFieldParam.setVisible(true);
        textFieldParam.setVisible(false);

        // Cambiar la imagen del botón a "ocultar"
        if (passwordFieldParam == passwordField) {
            ImageView imageView = (ImageView) toggleVisibilityButton1.getGraphic();
            imageView.setImage(new Image("resources/iconos/visualizar.png"));
        } else if (passwordFieldParam == confirmpasswordField) {
            ImageView imageView = (ImageView) toggleVisibilityButton2.getGraphic();
            imageView.setImage(new Image("resources/iconos/visualizar.png"));
        }
        // Recuperar el foco y colocar el cursor al final del texto sin seleccionar todo
        passwordFieldParam.requestFocus();
        passwordFieldParam.positionCaret(passwordFieldParam.getText().length());

    }

}
