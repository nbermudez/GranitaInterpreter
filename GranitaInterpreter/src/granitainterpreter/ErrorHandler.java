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

    private static ArrayList<String> errors = new ArrayList<String>();;
    private static ArrayList<String> warnings = new ArrayList<String>();;
    private static ErrorType error = new ErrorType();

    public static Type handle(String msg) {
        errors.add(msg);
        return error;
    }
    
    public static Type handleWarning(String msg) {
        warnings.add(msg);
        return error;
    }
    
    public static Type handleFatalError(String msg) throws GranitaException {
        System.err.println("fatal error: \n\t" + msg);
        System.exit(0);
        return error;
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
        if (!errors.isEmpty()){
            printErrors();
        }
        if (!warnings.isEmpty()) {
            printWarnings();
        }
    }
    
    public static boolean isEmpty() {
        return errors.isEmpty();
    }
    
    public static void cleanup() {
        errors.clear();
        warnings.clear();
    }
}
