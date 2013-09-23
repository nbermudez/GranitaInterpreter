/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter;

import granita.IR.General.D_Program;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.DataLayout.RE_Variable;
import granita.Interpreter.Results.Result;
import granita.Semantic.DataLayout.Context;
import granita.Semantic.DataLayout.ContextStack;
import granita.Semantics.SemanticUtils;
import java.util.Stack;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class Interpreter {
    
    private static ContextStack contextStack = new ContextStack();
    
    //<editor-fold defaultstate="collapsed" desc="Flow control related">    
    private static boolean returnReached = false;
    private static boolean breakReached = false;
    private static boolean continueReached = false;
    
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
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main methods">
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
    
    public static void registerContext(Context context) {
        contextStack.push(context);
        variableIndex.push(0);
    }
    
    public static void unregisterContext() {
        contextStack.pop().setParent(null);
        variableIndex.pop();
    }
    
    public static void setInitialEnvironment() {
        procedures = SemanticUtils.getInstance().getProcedures();
        
        RE_Variable[] globalA = SemanticUtils.getInstance().getGlobal();
        global = new Context();
        global.setContextId(0);
        global.setRuntimeEnvironment(globalA);
        registerContext(global);
    }
    
    public static Context currentContext() {
        return contextStack.peek();
    }
    
    public static Context globalContext() {
        return global;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Procedures/Global variables">
    private static Procedure[] procedures;
    private static Context global;
    
    public static RE_Variable getVariableRE(int index) {
        return global.getVariableRE(index);
    }
    
    public static Procedure getProcedure(int index) {
        return procedures[index];
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Return Value">
    private static Result EAX;
    
    public static Result getReturnValue() {
        return EAX;
    }
    
    public static void setReturnValue(Result value) {
        EAX = value;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Indexes management">
    private static Stack<Integer> variableIndex = new Stack<Integer>();
    private static int procedureIndex = 0;  
    
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
