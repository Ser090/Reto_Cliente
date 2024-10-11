/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ApplicationClientSignUpController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

