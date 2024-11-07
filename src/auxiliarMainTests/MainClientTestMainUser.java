/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliarMainTests;

import javafx.application.Application;
import javafx.stage.Stage;
import business.ApplicationClientFactory;
import utilidades.User;

/**
 *
 * @author 2dam
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
