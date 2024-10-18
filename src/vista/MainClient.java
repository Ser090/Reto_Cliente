/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import negocio.ApplicationClientFactory;

/**
 *
 * @author 2dam
 */
public class MainClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationClientFactory factory = ApplicationClientFactory.getInstance();

        //factory.loadSignInWindow(stage, "");
        factory.loadMainWindow(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
