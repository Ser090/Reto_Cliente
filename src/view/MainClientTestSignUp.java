/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.application.Application;
import javafx.stage.Stage;
import business.ApplicationClientFactory;

/**
 *
 * @author 2dam
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
