package vista;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import negocio.Client;

/**
 * FXML Controller class
 */
public class ApplicationClientPrincipalController implements Initializable {

    @FXML
    private MenuItem menuCerrarSesion;

    @FXML
    private MenuItem menuSalir;
    private Stage stage;
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Acciones para "Cerrar Sesión" y "Salir"

    }

    public void setClient(Client client) {
        this.client = client;
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
        System.out.println("Cerrando sesión...");
        // Lógica para cerrar la sesión
    }

    private void salirAplicacion() {
        System.out.println("Saliendo de la aplicación...");
        Stage stage = (Stage) menuSalir.getParentPopup().getOwnerWindow();
        stage.close();
    }
}
