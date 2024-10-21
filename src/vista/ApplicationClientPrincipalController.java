package vista;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import negocio.ApplicationClientFactory;
import negocio.Client;
import utilidades.User;

/**
 * FXML Controller class
 */
public class ApplicationClientPrincipalController implements Initializable {

    @FXML
    private MenuItem menuCerrarSesion;
    @FXML
    private MenuItem menuSalir;
    @FXML
    private Label welcomeLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button logoutButton;

    private ContextMenu contextMenu;

    private Stage stage;
    private Client client;
    private User user;
    private ApplicationClientFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        MenuItem clearFieldsItem = new MenuItem("Cerrar sesión");
        clearFieldsItem.setStyle("-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Protest Strike';"
                + "-fx-text-fill: #FFFFFF;"
                + "-fx-background-color: transparent;"
                + "-fx-max-width: 250px;"
                + "-fx-wrap-text: true;");
        clearFieldsItem.setOnAction(event -> cerrarSesion());

        // Opción "Salir"
        MenuItem exitItem = new MenuItem("Salir");
        exitItem.setStyle("-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: 'Protest Strike';"
                + "-fx-text-fill: #FFFFFF;"
                + "-fx-background-color: transparent;"
                + "-fx-max-width: 250px;"
                + "-fx-wrap-text: true;");
        exitItem.setOnAction(event -> salirAplicacion());

        // Añadir las opciones personalizadas al menú contextual
        contextMenu.getItems().addAll(clearFieldsItem, exitItem);

        // Asignar el menú contextual al GridPane
        anchorPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(anchorPane, event.getScreenX(), event.getScreenY());
            }
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Bienvenid@ " + user.getName());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        try {

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Programa principal");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);

            factory = ApplicationClientFactory.getInstance();

            logoutButton.setOnAction(null);

            logoutButton.setOnAction(event -> cerrarSesion());
            menuCerrarSesion.setOnAction(event -> cerrarSesion());
            menuSalir.setOnAction(event -> salirAplicacion());
            //scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
            stage.show();
        } catch (Exception e) {

        }
    }

    private void handleWindowShowing(javafx.event.Event event) {

    }

    private void cerrarSesion() {
        // Crear la alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");

        // Obtener la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, realizar la acción de cancelar
            factory.loadSignInWindow(stage, "");
        }
    }

    private void salirAplicacion() {
        // Crear la alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir de la aplicación?");

        // Obtener la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, realizar la acción de cancelar
            Stage stage = (Stage) menuSalir.getParentPopup().getOwnerWindow();
            stage.close();
        }
    }
}
