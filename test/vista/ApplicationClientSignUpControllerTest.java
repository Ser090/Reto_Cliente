/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.*;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignUpControllerTest extends ApplicationTest {

    public ApplicationClientSignUpControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainClientTestSignUp.class);
        //Thread.sleep(2000);
        //new MainClientTestSignUp().start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {

        new MainClientTestSignUp().start(stage);
    }

    @Test
    public void test_A_SignUpOK() {
        clickOn("#nameField");
        write("Manolo");
        clickOn("#surname1Field");
        write("Garcia");
        clickOn("#surname2Field");
        write("Garcia");
        clickOn("#emailField");
        write("manolo@garcia.es");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#confirmpasswordField");
        write("12345678A");
        clickOn("#streetField");
        write("Calle Anonima, 123");
        clickOn("#cityField");
        write("Bilbao");
        clickOn("#zipField");
        write("12345");

        clickOn("#btnRegistrar");

        verifyThat("El registro se ha realizado con éxito.", isVisible());

    }

    @Test
    public void test_B_SignUpErrorEmail() {

        clickOn("#emailField");
        write("manologarcia.es");
        clickOn("#btnRegistrar");
        verifyThat("#errorImageEmail", isVisible());

    }

}
