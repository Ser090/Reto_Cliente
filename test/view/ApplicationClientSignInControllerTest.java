package view;

import view.MainClient;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 *
 * @author Urko
 */
//Establece el orden de los metodos
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignInControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainClient().start(stage);
    }

//    @BeforeClass
//    public static void setUpClass() throws TimeoutException {
//        registerPrimaryStage();
//        setupApplication(MainClient.class);
//    }
    public ApplicationClientSignInControllerTest() {
    }

    //Poner usuario correcto en la base de datos
    @Test
    public void test_A_LoginOK() {
        clickOn("#loginField");
        write("manolo@garcia.es");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#loginButton");
        verifyThat("#logoutButton", isVisible());
    }

    //@Ignore
    @Test
    public void test_B_LoginBad() {
        clickOn("#loginField");
        write("testmal@testb.org");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#loginButton");
        verifyThat("El correo electrónico (login) y/o la contraseña incorrect@/s", isVisible());
        clickOn("Aceptar");
    }

    //@Ignore
    @Test
    public void test_C_LoginEmpty() {
        // Caso 1: Ambos campos están vacíos
        clickOn("#loginButton");
        verifyThat("Uno o varios campos incorrectos o vacíos. Mantenga el cursor encima de los campos para más información.", isVisible());
        clickOn("Aceptar");

        // Caso 2: Solo el campo de login está vacío
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("Uno o varios campos incorrectos o vacíos. Mantenga el cursor encima de los campos para más información.", isVisible());
        clickOn("Aceptar");

        // Limpia los campos para el siguiente caso
        clickOn("#passwordField");
        eraseText(9);

        // Caso 3: Solo el campo de contraseña está vacío
        clickOn("#loginField");
        write("testloginempty@testc.org");
        clickOn("#loginButton");
        verifyThat("Uno o varios campos incorrectos o vacíos. Mantenga el cursor encima de los campos para más información.", isVisible());
        clickOn("Aceptar");
    }

    //Poner usuario no activo en la base de datos
    //@Ignore
    @Test
    public void test_D_LoginNonActive() {
        clickOn("#loginField");
        write("incognito@noactivo.es");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#loginButton");
        verifyThat("El usuario introducido está desactivado, no puede hacer iniciar sesión comuniquese con el departamento de Sistemas.", isVisible());
        clickOn("Aceptar");
    }

}
