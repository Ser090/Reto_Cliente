package view;

import auxiliarMainTests.MainClientTestMainUser;
import javafx.stage.Stage;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
public class ApplicationClientMainUserControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainClientTestMainUser().start(stage);
    }

//    @BeforeClass
//    public static void setUpClass() throws TimeoutException {
//        registerPrimaryStage();
//        setupApplication(MainClient.class);
//    }
    public ApplicationClientMainUserControllerTest() {
    }

    //@Ignore
    @Test
    public void test_A_LogOutButton() {
        clickOn("#logoutButton");
        verifyThat("¿Estás seguro de que deseas cerrar sesión?", isVisible());
        clickOn("Aceptar");
        verifyThat("#loginButton", isVisible());
    }

    //@Ignore
    @Test
    public void test_B_GetOutOfHere() {
        clickOn("#menuApp");
        clickOn("#menuSalir");
        verifyThat("¿Estás seguro de que deseas salir de la aplicación?", isVisible());
        clickOn("Aceptar");
        assertFalse(lookup("#main").tryQuery().isPresent());
        assertFalse(lookup("#signIn").tryQuery().isPresent());
        assertFalse(lookup("#signUp").tryQuery().isPresent());
    }

}
