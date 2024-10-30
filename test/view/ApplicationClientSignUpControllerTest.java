/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.MainClientTestSignUp;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author Urko
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignUpControllerTest extends ApplicationTest {


    /*@BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainClientTestSignUp.class);
    }*/
    @Override
    public void start(Stage stage) throws Exception {
        new MainClientTestSignUp().start(stage);
    }

    public ApplicationClientSignUpControllerTest() {
    }

    @Test
    public void test_A_SignUp_EmptyTexts() {

        clickOn("#btnRegistrar");
        verifyThat("Uno o varios campos incorrectos o vacios, mantenga el cursor encima de los campos para más información.", isVisible());
        clickOn("Aceptar");
        verifyThat("#errorImageName", isVisible());
        verifyThat("#errorImageSurname1", isVisible());
        verifyThat("#errorImageSurname2", isVisible());
        verifyThat("#errorImageEmail", isVisible());
        verifyThat("#errorImagePass", isVisible());
        verifyThat("#errorImagePassRepeat", isVisible());
        verifyThat("#errorImageStreet", isVisible());
        verifyThat("#errorImageCity", isVisible());
        verifyThat("#errorImageZip", isVisible());

    }

    @Test
    public void test_B_SignUp_WrongData_AND_ClearFields() {

        clickOn("#nameField");
        write("Manolo");
        clickOn("#surname1Field");
        write("Garcia");
        clickOn("#surname2Field");
        write("Garcia");
        clickOn("#emailField");
        write("manologarcia.es");
        clickOn("#passwordField");
        write("1234b");
        clickOn("#confirmpasswordField");
        write("1234ab");
        clickOn("#streetField");
        write("Calle Anonima, 123");
        clickOn("#cityField");
        write("Bilbao");
        clickOn("#zipField");
        write("1234");
        clickOn("#btnRegistrar");

        verifyThat("Uno o varios campos incorrectos o vacios, mantenga el cursor encima de los campos para más información.", isVisible());
        clickOn("Aceptar");

        verifyThat("#errorImageEmail", isVisible());
        verifyThat("#errorImagePass", isVisible());
        verifyThat("#errorImagePassRepeat", isVisible());
        verifyThat("#errorImageZip", isVisible());

        /*clickOn(MouseButton.SECONDARY);
        clickOn("Borrar campos");

        verifyThat("#nameField", hasText(""));
        verifyThat("#surname1Field", hasText(""));
        verifyThat("#surname2Field", hasText(""));
        verifyThat("#emailField", hasText(""));
        verifyThat("#passwordField", hasText(""));
        verifyThat("#confirmpasswordField", hasText(""));
        verifyThat("#streetField", hasText(""));
        verifyThat("#cityField", hasText(""));
        verifyThat("#zipField", hasText(""));*/
    }

    @Test
    public void test_C_SignUp_OK() {
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

        clickOn("Aceptar");
    }

    @Test
    public void test_D_SignUp_LoginRepeatWrong() {
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

        verifyThat("El correo electronico ya existe en la base de datos.", isVisible());

    }

}
