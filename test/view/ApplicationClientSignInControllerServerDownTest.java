/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignInControllerServerDownTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainClient().start(stage);
    }

    public ApplicationClientSignInControllerServerDownTest() {
    }
    @Ignore
    @Test
    public void test_A_ServerOff() {
        clickOn("#loginField");
        write("pruebapruebita@gmail.com");
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("Servidor no encontrado, inténtelo de nuevo...", isVisible());
        clickOn("Aceptar");

    }

    //@Ignore
    @Test
    public void test_B_MaxConnections() {
        //Previamente o ponemos conexiones de pool a 0
        clickOn("#loginField");
        write("pruebapruebita@gmail.com");
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("Error de conexion con la base de datos,  no hay conexión disponible, inténtelo de nuevo...", isVisible());
        clickOn("Aceptar");
    }


}
