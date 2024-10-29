package view;

import view.MainClient;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 *
 * @author Sergio
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

    @Test
    public void test_C_LoginEmpty() {
        // Caso 1: Ambos campos están vacíos
        clickOn("#loginButton");
        verifyThat("Todos los campos deben ser rellenados.", isVisible());
        clickOn("Aceptar");

        // Caso 2: Solo el campo de login está vacío
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("Todos los campos deben ser rellenados.", isVisible());
        clickOn("Aceptar");

        // Limpia los campos para el siguiente caso
        clickOn("#passwordField");
        write("");

        // Caso 3: Solo el campo de contraseña está vacío
        clickOn("#loginField");
        write("sergio@sergio.org");
        clickOn("#loginButton");
        verifyThat("Todos los campos deben ser rellenados.", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void test_D_LoginNonActive() {
        //hay que cambiar el login a uno que tengamos sin el activo
        clickOn("#loginField");
        write("sergio@sergio.org");
        clickOn("#passwordField");
        write("12345678A");
        clickOn("#loginButton");
        verifyThat("El usuario introducido esta desactivado, no puede hacer login.", isVisible());
        clickOn("Aceptar");
    }

}
