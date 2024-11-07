/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import auxiliarMainTests.MainClientTestSignUp;
import java.math.BigInteger;
import java.util.Random;
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
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author Urko
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignUpControllerTest extends ApplicationTest {

    private static String textoClave;

    //Esto  se ejecuta una vez antes de lanzar el Main
    @BeforeClass
    public static void setUpClass() throws Exception {
        /*La expresión  genera un número aleatorio de 25 cifras. Si el número tiene menos
        de 25 cifras, completa con ceros. 83 bits son suficientes para representar un
        número 10^25 que garantiza 25 cifras.*/
        textoClave = String.format("%025d", new BigInteger(83, new Random()));

        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainClientTestSignUp.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new MainClientTestSignUp().start(stage);
    }

    @Test
    public void test_A_SignUp_EmptyTexts() {

        clickOn("#btnRegistrar");
        verifyThat("Uno o varios campos incorrectos o vacíos. Mantenga el cursor encima de los campos para más información.", isVisible());
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
        write(textoClave + "gmail.es");
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

        verifyThat("Uno o varios campos incorrectos o vacíos. Mantenga el cursor encima de los campos para más información.", isVisible());
        clickOn("Aceptar");

        verifyThat("#errorImageEmail", isVisible());
        verifyThat("#errorImagePass", isVisible());
        verifyThat("#errorImagePassRepeat", isVisible());
        verifyThat("#errorImageZip", isVisible());

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
        write(textoClave + "@tartanga.es");
        //write("manolo@garcia.es"); // Esta linea solo es en el caso de que la base de datos este limpia
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
        write("manolo@garcia.es"); // Correo controlado previo a introducirlo en la base de datos

        /*Este se puede usar para aprobechar el la insercion del testC
          solo para cuando se ejecuta previamente el C*/
        //write(textoClave + "@tartanga.es");
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

        verifyThat("El correo electrónico ya existe en la base de datos.", isVisible());

        clickOn("Aceptar");
    }

    @Test
    public void test_E_SignUp_NonActiveUser() {
        textoClave = String.format("%025d", new BigInteger(83, new Random()));

        clickOn("#nameField");
        write("Tipo");
        clickOn("#surname1Field");
        write("De");
        clickOn("#surname2Field");
        write("Incognito");
        clickOn("#emailField");
        //write(textoClave + "@noactivo.es");
        write("incognito@noactivo.es");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#confirmpasswordField");
        write("12345678A");
        clickOn("#streetField");
        write("Calle Aleatoria, -1");
        clickOn("#cityField");
        write("Cuenca");
        clickOn("#zipField");
        write("99991");

        clickOn("#activeCheckBox");
        verifyThat("#warningbox", isVisible());

        clickOn("#activeCheckBox");
        verifyThat("#warningbox", isInvisible());

        clickOn("#activeCheckBox");
        verifyThat("#warningbox", isVisible());

        clickOn("#btnRegistrar");

        verifyThat("Si el usuario esta 'No Activo', no podrá iniciar sesión ¿Desea continuar el registro?", isVisible());

        clickOn("Aceptar");

        verifyThat("El registro se ha realizado con éxito.", isVisible());

        clickOn("Aceptar");

    }
}
