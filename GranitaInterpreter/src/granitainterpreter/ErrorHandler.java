/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ErrorHandler {

    private static ArrayList<String> errors;
    private static ArrayList<String> warnings;

    public static Type handle(String msg) {
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        errors.add(msg);
        return new ErrorType();
    }
    
    public static Type handleWarning(String msg) {
        if (warnings == null) {
            warnings = new ArrayList<String>();
        }
        warnings.add(msg);
        return new ErrorType();
    }

    public static void printErrors() {
        if (errors != null) {
            System.err.println(errors.size() + " errors found: ");
            for (String string : errors) {
                System.err.println("\t" + string);
            }
        }
    }
    
    public static void printWarnings() {
        if (warnings != null) {
            System.err.println(warnings.size() + " warnings found: ");
            for (String string : warnings) {
                System.err.println("\t" + string);
            }
        }
    }
    
    public static void printAll(){ 
        printErrors();
        printWarnings();
    }
}
