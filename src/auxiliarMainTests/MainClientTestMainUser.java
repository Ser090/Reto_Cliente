package auxiliarMainTests;

import javafx.application.Application;
import javafx.stage.Stage;
import business.ApplicationClientFactory;
import utilidades.User;

/**
 * Este Main es unicamente para poder ejecutar los test del Main
 *
 * @author Sergio
 */
public class MainClientTestMainUser extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationClientFactory factory = ApplicationClientFactory.getInstance();
        User user = new User();
        user.setName("UsuarioPrueba");
        factory.loadMainUserWindow(stage, user);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
