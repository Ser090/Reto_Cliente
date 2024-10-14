/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ApplicationClientSignUpController implements Initializable {

    private Stage stage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //Ejemplo JAVI
    public void initStage(Parent root) {
        try {
            LOGGER.info("Inicializando la carga del stage");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL); //Modal
            stage.setTitle("Registro de Usuario");
            stage.setResizable(false);
            stage.setOnShowing(this::handleWindowShowing);
            btAceptar.addEventHandler(ActionEvent.ACTION, this::handleButtonAceptarAction);
            stage.show();
            //stage.showAndWait(); //Modal
        } catch  {
            LOGGER.log(Level.SEVERE, "Texto del error");
        }

    }

    /*
    @FXML
    private ComboBox<String> provincia;

    public void initialize() {
        loadProvinces();
    }
// Método que carga las provincias desde la base de datos y las agrega a un ComboBox.

private void loadProvinces() {
    // Creamos una lista para almacenar los nombres de las provincias.
    ArrayList<String> provinciasList = new ArrayList<>();

    // Intentamos establecer una conexión a la base de datos y ejecutar la consulta.
    try (Connection conn = DatabaseConnection.getConnection(); // Se obtiene una conexión a la base de datos.
         Statement stmt = conn.createStatement(); // Se crea un objeto Statement para ejecutar la consulta.
         ResultSet rs = stmt.executeQuery("SELECT name FROM res_country_state")) { // Ejecuta la consulta SQL para obtener los nombres de las provincias.

        // Itera sobre los resultados de la consulta.
        while (rs.next()) {
            // Agrega el nombre de cada provincia a la lista.
            provinciasList.add(rs.getString("name"));
        }
        // Añade todos los elementos de la lista al ComboBox 'provincia'.
        provincia.getItems().addAll(provinciasList);
    } catch (Exception e) {
        // Si ocurre algún error durante la conexión o la ejecución de la consulta, se imprime la traza de la excepción.
        e.printStackTrace();
    }/*

     */
}
