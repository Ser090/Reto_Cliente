/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import javafx.scene.control.Alert;

/**
 *
 * @author 2dam
 */
public class AlertUtilities {

    public static void showErrorDialog(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
