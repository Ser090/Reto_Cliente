/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.concurrent.TimeoutException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import static org.testfx.api.FxToolkit.*;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 *
 * @author Sergio
 */
//Establece el orden de los metodos
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignInControllerTest extends ApplicationTest {
    /*@Override
    public void start(Stage stage) throws Exception {
       new MainClient().start(stage);
    }*/
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        registerPrimaryStage();
        setupApplication(MainClient.class);
    }

    public ApplicationClientSignInControllerTest() {
    }

    @Test
    public void testSomeMethod() {
    }

    @Test
    public void test_A_LoginOK() {
        clickOn("#loginField");
        write("pruebapruebita@gmail.com");
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("#logoutButton", isVisible());
    }

    @Test
    public void test_B_LoginBad() {
        clickOn("#loginField");
        write("sergio@sergio.org");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#loginButton");
        verifyThat("El correo electrónico (login) y/o la contraseña incorrect@/s", isVisible());
        clickOn("Aceptar");
    }

}
