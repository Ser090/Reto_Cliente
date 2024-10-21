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
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
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
    private Button logoutButton;

    private Stage stage;
    private Client client;
    private User user;
    private ApplicationClientFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Acciones para "Cerrar Sesión" y "Salir"

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
