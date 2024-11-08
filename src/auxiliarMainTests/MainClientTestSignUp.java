package auxiliarMainTests;

import javafx.application.Application;
import javafx.stage.Stage;
import business.ApplicationClientFactory;

/**
 * Este Main es unicamente para poder ejecutar los test del SignUp
 *
 * @author Urko
 */
public class MainClientTestSignUp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationClientFactory factory = ApplicationClientFactory.getInstance();

        factory.loadSignUpWindow(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
