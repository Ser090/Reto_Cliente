package vista;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import negocio.Client;
import utilidades.User;

/**
 * Controlador para la pantalla principal de la aplicación.
 */
public class ApplicationClientPrincipalController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientPrincipalController.class.getName());
    private Stage stage;
    private Client client;
    private User usuarioActual;

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUsuarioActual(User usuarioActual) {
        this.usuarioActual = usuarioActual;
        actualizarMensajeBienvenida();
    }

    private void actualizarMensajeBienvenida() {
        if (usuarioActual != null) {
            welcomeLabel.setText("¡Bienvenido! " + usuarioActual.getName());
        } else {
            LOGGER.severe("El usuario actual es nulo. No se puede mostrar el nombre.");
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        LOGGER.info("El usuario ha presionado el botón de cerrar sesión.");
        if (stage != null) {
            stage.close(); 
        } else {
            LOGGER.severe("El stage es nulo. No se puede cerrar la ventana.");
        }
    }
}
