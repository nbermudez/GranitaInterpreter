/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter;

import granita.DataLayout.Context;
import granita.DataLayout.ContextStack;
import granita.IR.General.D_Program;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.DataLayout.RE_Variable;
import granita.SymbolTable.SymbolTableTree;
import java.util.Stack;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Interpreter {
    
    //<editor-fold defaultstate="collapsed" desc="Working but not as teacher wanted.">
        
    private static ContextStack contextStack = new ContextStack();
    private static boolean returnReached = false;
    private static boolean breakReached = false;
    private static boolean continueReached = false;
    private static Object returnedValue = null;
    
    public static void interpret(D_Program program) {
        setInitialEnvironment();
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
        variableIndex.push(0);
    }
    
    public static void unregisterContext() {
        contextStack.pop().setParent(null);
        variableIndex.pop();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Doing interpretation again T.T">
    private static Procedure[] procedures;
    private static RE_Variable[] global;
    private static int procedureIndex = 0;
    private static Stack<Integer> variableIndex = new Stack<Integer>();
    
    public static void setInitialEnvironment() {
        //tomar la tabla de simbolos y generar Procedure[] y global[]
        int sizeFunctions = SymbolTableTree.getInstance().getFunctionsCount();
        procedures = new Procedure[sizeFunctions];
        
        int sizeGlobal = SymbolTableTree.getInstance().getGlobalCount();
        global = new RE_Variable[sizeGlobal];
    }
    
    public static int getProcedureIndex() {
        int value = procedureIndex;
        procedureIndex ++;
        return value;
    }
    
    public static int getVariableIndex() {
        int value = variableIndex.pop();
        variableIndex.push(value + 1);
        return value;
    }
    //</editor-fold> 
}
