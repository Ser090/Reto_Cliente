package server;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import auxiliarMainTests.MainClientTestSignUp;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Sergio
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignUpServerDownTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainClientTestSignUp().start(stage);
    }

    public ApplicationClientSignUpServerDownTest() {
    }

    //@Ignore
    @Test
    public void test_A_ServerOff() {
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
        verifyThat("Servidor no encontrado, inténtelo de nuevo...", isVisible());
        clickOn("Aceptar");
    }

    @Ignore
    @Test
    public void test_B_MaxConnections() {
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

        verifyThat("Error de conexión con la base de datos. No hay conexión disponible, inténtelo de nuevo...", isVisible());
        clickOn("Aceptar");
    }

}
