package ch.schule.MAP;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormValidation {
    public static boolean textFieldNotEmpty(TextField t){
        boolean b = false;
        if(t.getText() != null && !t.getText().isEmpty()){
            b = true;
        }
        return b;
    }

    public static boolean textFieldNotEmpty(TextField t, Label l, String validationText){
        boolean b = false;
        String c = null;
        if(t.getText() == null || t.getText().isEmpty()){
            b = true;
            c = validationText;
        }
        l.setText(c);
        return b;
    }
}
