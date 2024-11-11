package business;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utilidades.Signable;
import utilidades.User;
import view.ApplicationClientMainUserController;
import view.ApplicationClientSignInController;
import view.ApplicationClientSignUpController;

/**
 * La clase {@code ApplicationClientFactory} es un singleton que se encarga de
 * gestionar la creación y el manejo de las ventanas de la aplicación cliente.
 * Proporciona métodos para cargar diferentes escenas como el inicio de sesión,
 * registro y la ventana principal del usuario, así como para cerrar ventanas y
 * aplicar estilos CSS.
 *
 * @author Sergio
 */
public class ApplicationClientFactory {

    private static final Logger LOGGER = Logger.getLogger(ApplicationClientFactory.class.getName());
    private static ApplicationClientFactory instance; // Instancia única de la clase
    private static Client client; // Instancia del cliente que maneja la conexión

    // Constructor privado para evitar instanciación externa
    private ApplicationClientFactory() {
    }

    /**
     * Obtiene la instancia única de la fábrica de la aplicación cliente.
     *
     * @return La instancia de {@code ApplicationClientFactory}.
     */
    public static ApplicationClientFactory getInstance() {
        if (instance == null) {
            instance = new ApplicationClientFactory();
            client = Client.getInstance(); // Inicializa el cliente al crear la instancia
        }
        return instance;
    }

    /**
     * Carga la ventana de inicio de sesión en el escenario proporcionado.
     *
     * @param stage El escenario donde se mostrará la ventana de inicio de
     * sesión.
     * @param login El nombre de usuario que se utilizará para el inicio de
     * sesión.
     */
    public void loadSignInWindow(Stage stage, String login) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLSignIn.fxml"));
            Parent root = loader.load(); // Carga el archivo FXML
            ApplicationClientSignInController controller = loader.getController();
            controller.setStage(stage); // Asigna el escenario al controlador
            controller.setLogin(login); // Establece el login en el controlador
            controller.initStage(root); // Inicializa la escena con el root cargado
        } catch (IOException event) {
            LOGGER.log(Level.SEVERE, "Error al abrir la ventana de inicio de sesión: {0}", event.getMessage());
            showErrorDialog("No se pudo cargar la vista de inicio de sesión."); // Muestra un error si falla
        }
    }

    /**
     * Carga la ventana de registro en el escenario proporcionado.
     *
     * @param stage El escenario donde se mostrará la ventana de registro.
     */
    public void loadSignUpWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLSignUp.fxml"));
            Parent root = loader.load(); // Carga el archivo FXML
            ApplicationClientSignUpController controller = loader.getController();
            controller.setStage(stage); // Asigna el escenario al controlador
            controller.initStage(root); // Inicializa la escena con el root cargado
        } catch (IOException event) {
            LOGGER.log(Level.SEVERE, "Error al abrir la ventana de registro: {0}", event.getMessage());
            showErrorDialog("No se pudo cargar la vista de registro."); // Muestra un error si falla
        }
    }

    /**
     * Carga la ventana principal del usuario en el escenario proporcionado.
     *
     * @param stage El escenario donde se mostrará la ventana principal del
     * usuario.
     * @param user El objeto usuario que contiene la información del usuario
     * actual.
     */
    public void loadMainUserWindow(Stage stage, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/FXMLMain.fxml"));
            Parent root = loader.load(); // Carga el archivo FXML
            ApplicationClientMainUserController controller = loader.getController();
            controller.setClient(client); // Establece el cliente en el controlador
            controller.setUser(user); // Establece el usuario en el controlador
            controller.setStage(stage); // Asigna el escenario al controlador
            controller.initStage(root); // Inicializa la escena con el root cargado
        } catch (IOException event) {
            LOGGER.log(Level.SEVERE, "Error al abrir la ventana principal: {0}", event.getMessage());
            showErrorDialog("No se pudo cargar la vista principal."); // Muestra un error si falla
        }
    }

    /**
     * Cierra la ventana especificada.
     *
     * @param stage El escenario que se desea cerrar.
     */
    public void closeWindow(Stage stage) {
        if (stage != null) {
            stage.close(); // Cierra la ventana si no es nula
        }
    }

    /**
     * Muestra un diálogo de error con el mensaje proporcionado.
     *
     * @param message El mensaje que se mostrará en el diálogo de error.
     */
    private void showErrorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error"); // Título del diálogo de error
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(message); // Contenido del mensaje
        alert.showAndWait(); // Muestra el diálogo y espera la respuesta del usuario
    }

    /**
     * Aplica una hoja de estilo CSS a la escena especificada.
     *
     * @param scene La escena a la que se le aplicará la hoja de estilo.
     * @param cssFileName El nombre del archivo CSS que se aplicará.
     */
    public void applyStyle(Scene scene, String cssFileName) {
        scene.getStylesheets().clear(); // Limpia las hojas de estilo actuales
        scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm()); // Añade la nueva hoja de estilo
    }
    /**
     * Retorna el cliente para ser usado al llamar a la factoria
     *
     * @return
     */
    public Signable access() {
        return client;
    }
}
