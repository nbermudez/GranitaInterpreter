/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Misc;

import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.Type;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static String getErrors() {
        String result = "";
        if (errors != null) {
            result += errors.size() + " errors found: \n";
            for (String string : errors) {
                result += "\t" + string + "\n";
            }
        }
        return result + "\n";
    }
    
    public static String getWarnings() {
        String result = "";
        if (warnings != null) {
            result += warnings.size() + " warnings found: \n";
            for (String string : warnings) {
                result += "\t" + string + "\n";
            }
        }
        return result + "\n";
    }
    
    public static String getAll() {
        String r = "", r2 = "";
        if (!errors.isEmpty()) {
            r = getErrors();
        }
        if (!warnings.isEmpty()) {
            r2 = getWarnings();
        }
        return r + r2;
    }
    
    public static boolean isEmpty() {
        return errors.isEmpty();
    }
    
    public static void cleanup() {
        errors.clear();
        warnings.clear();
    }
}
