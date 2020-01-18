package ch.schule.MAP;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

public class MainAppTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(MainApp.class.getResource("/fxml/PersonOverview.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void createNew () {
        //ELEMENTS
        clickOn("#firstname");
        write("Firstname");
        clickOn("#lastname");
        write("Lastname");
        clickOn("#eMail");
        write("a.b@c.com");
        clickOn("#school");
        write("School");
        clickOn("#subject");
        write("Subject");
        clickOn("#btnNew");

        //CHECK THE VALUES THAT ARE STORED
        verifyThat("#firstname", hasText("Firstname"));
        verifyThat("#lastname", hasText("Lastname"));
        verifyThat("#eMail", hasText("a.b@c.com"));
        verifyThat("#school", hasText("School"));
        verifyThat("#subject", hasText("Subject"));
    }

    @Test
    public void editValues() {
        clickOn("#firstname");
        write("Firstname");
        clickOn("#lastname");
        write("Lastname");
        clickOn("#eMail");
        write("a.b@c.com");
        clickOn("#school");
        write("School");
        clickOn("#subject");
        write("Subject");
        clickOn("#btnNew");

        //EDIT
        clickOn("#firstname");
        write("Edited");
        clickOn("#btnEdit");

        clickOn("#firstNameCol");
        /* todo
        *   access sidebar to check if values have changed
        *   -> and then click on them again the check the values.
        * */
    }

    @Test
    public void delValues() {
        clickOn("#firstname");
        write("Firstname");
        clickOn("#lastname");
        write("Lastname");
        clickOn("#eMail");
        write("a.b@c.com");
        clickOn("#school");
        write("School");
        clickOn("#subject");
        write("Subject");

        clickOn("#btnDel");
       /* todo
           find out how to test if a texField has no Text inside
        */
        /*verifyThat("#firstname", hasText(""));
        verifyThat("#lastname", hasText(""));
        verifyThat("#eMail", hasText(""));
        verifyThat("#school", hasText(""));
        verifyThat("#subject", hasText(""));*/
    }
}