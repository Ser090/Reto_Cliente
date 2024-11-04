package view;

import business.ApplicationClientFactory;
import business.Client;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
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
import javafx.stage.Stage;
import utilidades.Message;
import utilidades.User;
import static utilities.AlertUtilities.showErrorDialog;
import static utilities.ValidateUtilities.isValid;

/**
 *
 * @author Lucian
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
    private TextField passwordFieldVisual;
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
    @FXML
    private Button toggleVisibilityButton;

    private ContextMenu contextMenu;

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
        assignCustomContextMenu(loginField);
        assignCustomContextMenu(passwordField);

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
    }

    private void handleExit() {
        // Obtener el Stage a través del GridPane (o cualquier otro nodo de la ventana)
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();  // Cierra la ventana
    }

    private void assignCustomContextMenu(TextField textField) {
        // Asignar el menú contextual personalizado y eliminar el predeterminado
        textField.setContextMenu(contextMenu);
    }

    private void handleClearFields() {
        loginField.clear();
        passwordField.clear();
        label.requestFocus();
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
            toggleVisibilityButton.setOnMousePressed(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    togglePasswordVisibility();
                }
            });

            toggleVisibilityButton.setOnMouseReleased(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    togglePasswordVisibilityReleased();
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
            if (event.isAltDown() && event.getCode() == KeyCode.I) {
                loginButton.fire();  // Simula el clic en el botón Iniciar sesión
                event.consume();  // Evita la propagación adicional del evento
            } else if (event.isAltDown() && event.getCode() == KeyCode.R) {
                registerLink.fire();  // Simula el clic en el hipervínculo Registrar
                event.consume();  // Evita la propagación adicional del evento
            }
        });
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
        ApplicationClientFactory.getInstance().loadSignUpWindow(stage);
    }

    @FXML
    private void handleButtonLoginButton(ActionEvent event) {
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
        // Validar campos específicos como contraseña y correo electrónico
        passwordField.setText(passwordField.getText().trim());
        if (!isValid(passwordField.getText(), "pass")) {
            showErrorImage(passwordField);
            hasError = true;
        }

        if (!isValid(loginField.getText(), "email")) {
            showErrorImage(loginField);
            hasError = true;
        }

        // Si hay errores, no continuar
        if (hasError) {
            LOGGER.severe("Hay errores en el formulario.");
            // Volver a habilitar el botón si hay errores
            showErrorDialog(AlertType.ERROR, "Uno o varios campos incorrectos o vacios, mantenga el cursor encima de los campos para más información.");
        } else {

            // Si no hay errores, proceder con el formulario
            user = new User();
            user.setLogin(loginField.getText());
            user.setPass(passwordField.getText());

            LOGGER.info("Validación de campos correcta.");
            Message response = client.signIn(user);
            messageManager(response);
        }

    }

    private void messageManager(Message message) {
        switch (message.getType()) {
            case LOGIN_OK:
                // Aviso de registro correcto y vuelta a la ventana de sign in
                loginButton.setDisable(true);
                factory.loadMainUserWindow(stage, (User) message.getObject());
                break;
            case SIGNIN_ERROR:
                loginField.setStyle("-fx-border-color: red;");
                passwordField.setStyle("-fx-border-color: red;");
                errorImageLogin.setVisible(true);
                errorImagePass.setVisible(true);
                showErrorDialog(Alert.AlertType.ERROR, "El correo electrónico (login) y/o la contraseña incorrect@/s");
                break;
            case BAD_RESPONSE:
                showErrorDialog(Alert.AlertType.ERROR, "Error interno de la base de datos, inténtelo de nuevo...");
                break;
            case CONNECTION_ERROR:
                showErrorDialog(Alert.AlertType.ERROR, "Error de conexion con la base de datos,  no hay conexión disponible, inténtelo de nuevo...");
                break;
            case SERVER_ERROR:
                showErrorDialog(Alert.AlertType.ERROR, "Servidor no encontrado, inténtelo de nuevo...");
                break;
            case NON_ACTIVE:
                showErrorDialog(Alert.AlertType.ERROR, "El usuario introducido esta desactivado, no puede hacer login.");
                break;

        }
    }

    private boolean areAllFieldsFilled() {
        for (Node node : gridPane.getChildren()) {
            if ((node instanceof TextField || node instanceof PasswordField) && (node != passwordFieldVisual)) {
                if (((TextField) node).getText() == null || ((TextField) node).getText().isEmpty()) {
                    LOGGER.severe("Error: El campo " + ((TextField) node).getPromptText() + " está vacío.");
                    return false;
                }
            }
        }
        return true;
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

    private void togglePasswordVisibility() {

        // Mostrar el TextField y ocultar el PasswordField
        passwordFieldVisual.setText(passwordField.getText());  // Copiar contenido del PasswordField al TextField
        passwordField.setVisible(false);
        passwordFieldVisual.setVisible(true);

        // Cambiar la imagen del botón a "mostrar"
        ImageView imageView = (ImageView) toggleVisibilityButton.getGraphic();
        imageView.setImage(new Image("resources/iconos/ocultar.png"));

    }

    private void togglePasswordVisibilityReleased() {

        // Mostrar el PasswordField y ocultar el TextField
        passwordField.setText(passwordFieldVisual.getText());  // Copiar contenido del TextField al PasswordField
        passwordField.setVisible(true);
        passwordFieldVisual.setVisible(false);

        ImageView imageView = (ImageView) toggleVisibilityButton.getGraphic();
        imageView.setImage(new Image("resources/iconos/visualizar.png"));

    }
}
