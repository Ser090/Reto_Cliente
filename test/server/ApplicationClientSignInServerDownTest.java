package server;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.*;
import org.testfx.framework.junit.ApplicationTest;
import view.MainClient;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 *
 * @author Lucian
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationClientSignInServerDownTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainClient().start(stage);
    }

    public ApplicationClientSignInServerDownTest() {
    }

    //@Ignore
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

    @Ignore
    @Test
    public void test_B_MaxConnections() {
        //Previamente o ponemos conexiones de pool a 0
        clickOn("#loginField");
        write("pruebapruebita@gmail.com");
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("Error de conexión con la base de datos. No hay conexión disponible, inténtelo de nuevo...", isVisible());
        clickOn("Aceptar");
    }

}
