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

    //FALTA TERMINAR
    @Test
    public void test_A_LoginOK() {
        clickOn("#logoutButton");
        write("pruebapruebita@gmail.com");
        clickOn("#passwordField");
        write("12345678U");
        clickOn("#loginButton");
        verifyThat("#logoutButton", isVisible());
    }

}
