/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 2dam
 */
public class ValidateUtilities {

    // Logger para registrar eventos y errores
    private static final Logger LOGGER = Logger.getLogger(ValidateUtilities.class.getName());

    public static Boolean isValid(String validacion, String type) {
        ResourceBundle bundle = ResourceBundle.getBundle("utilities.pattern");
        String patternType = "";
        switch (type) {
            case "email":
                patternType = bundle.getString("EMAILPATTERN");
                break;
            case "pass":
                patternType = bundle.getString("PASSPATTERN");
                break;
            case "zip":
                patternType = bundle.getString("ZIPPATTERN");
                break;
            default:
                LOGGER.severe("Tipo no encontrado");
                return false;
        }

        Pattern patron = Pattern.compile(patternType);
        Matcher matcher = patron.matcher(validacion);
        return matcher.matches();
    }

}
