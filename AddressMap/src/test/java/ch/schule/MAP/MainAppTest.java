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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
        clickOn("#saveButton");

        //CHECK THE VALUES THAT ARE STORED
        verifyThat("#firstname", hasText("Firstname"));
        verifyThat("#lastname", hasText("Lastname"));
        verifyThat("#eMail", hasText("a.b@c.com"));
        verifyThat("#school", hasText("School"));
        verifyThat("#subject", hasText("Subject"));
    }

    @Test
    public void editValues() {
        /*
         PROBLEM:
         Da es nicht möglich ist über TestFX auf die Tabelle zuzugreifen, kann CRUD nicht
         korrekt getestet werden, denn um Werte zu editieren muss zuerst an der Seite eine Person
         ausgewählt werden.
        */
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
        clickOn("#saveButton");

        //EDIT
        clickOn("#firstname");
        write("Edited");
        clickOn("#saveButton");
    }

    @Test
    public void delValues() {
        /*
         PROBLEM:
         Da es nicht möglich ist über TestFX auf die Tabelle zuzugreifen, kann die delete Funkion nicht
         korrekt getestet werden, denn um Werte zu löschen muss zuerst an der Seite eine Person
         ausgewählt werden.
        */

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
        clickOn("#deleteButton");
    }

    @Test
    public void searchPeople() {
        /*
         PROBLEM:
         Da es nicht möglich ist über TestFX auf die Tabelle zuzugreifen, kann die search Funktion nicht
         korrekt getestet werden, denn um die Suchresultate zu vergleichen müsste man auf die Tabelle zugreifen.
        */

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
        clickOn("#saveButton");

        //search part
        clickOn("#search");
        write("Firstname");
    }

    @Test
    public void sendMail() {
        /*
         PROBLEM:
         Da es nicht möglich ist über TestFX auf die Tabelle zuzugreifen, kann die Mail Funktion nicht
         korrekt getestet werden, denn um eine Mail zu verschicken müsste man auf die Tabelle zugreifen können.
        */

        clickOn("#mailButton");
    }
}