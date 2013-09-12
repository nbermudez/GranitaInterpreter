/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter;

import granita.DataLayout.Context;
import granita.DataLayout.ContextStack;
import granita.DataLayout.Procedure;
import granita.IR.General.D_Program;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Interpreter {
    private static ContextStack contextStack = new ContextStack();
    private static boolean returnReached = false;
    private static boolean breakReached = false;
    private static boolean continueReached = false;
    private static Object returnedValue = null;
    
    public static void interpret(D_Program program) {
        program.execute();
    }
    
    public static void saveContext() {
        contextStack.saveContext();
    }
    
    public static void loadContext() {
        contextStack.loadContext();
    }
    
    public static void returnWasReached(boolean reached) {
        returnReached = reached;
    }
    
    public static boolean returnReached() {
        return returnReached;
    }
    
    public static void breakWasReached(boolean reached) {
        breakReached = reached;
    }
    
    public static boolean breakReached() {
        return breakReached;
    }
    
    public static void continueWasReached(boolean reached) {
        continueReached = reached;
    }
    
    public static boolean continueReached() {
        return continueReached;
    }
    
    public static Object returnedValue() {
        return returnedValue;
    }
    
    public static void returnedValue(Object retValue) {
        returnedValue = retValue;
    }
    
    public static Context currentContext() {
        return contextStack.peek();
    }
    
    public static void registerContext(Context context) {
        contextStack.push(context);
    }
    
    public static void unregisterContext() {
        contextStack.pop().setParent(null);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Doing interpretation again T.T">
    private ArrayList<Procedure> procedures;
    //</editor-fold> 
}
