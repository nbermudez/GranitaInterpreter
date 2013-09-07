/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter;

import granita.DataLayout.ContextStack;
import granita.IR.General.D_Program;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Interpreter {
    private static ContextStack contextStack = new ContextStack();
    
    public static void interpret(D_Program program) {
        program.execute();
    }
    
    public static void saveContext() {
        contextStack.saveContext();
    }
    
    public static void loadContext() {
        contextStack.loadContext();
    }
}
