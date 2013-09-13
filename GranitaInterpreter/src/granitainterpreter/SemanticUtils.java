/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.DataLayout.Context;
import granita.DataLayout.ContextStack;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.DataLayout.RE_Variable;
import granita.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SemanticUtils {
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">

    private boolean mustReturnExpression = false;
    private int unreachableStatement = 0;
    private Type expectedReturnType = null;
    private boolean leftValueAsLocation = false;
    private boolean insidePrint = false;
    private boolean insideRead = false;
    private boolean mustMergeWithTemp = false;
    private ContextStack contextStack;
    private Context tmp;
    //</editor-fold>    

    private SemanticUtils() {
        contextStack = new ContextStack();
    }

    public static SemanticUtils getInstance() {
        return UtilsHolder.INSTANCE;
    }

    private static class UtilsHolder {

        private static final SemanticUtils INSTANCE = new SemanticUtils();
    }

    public boolean mustReturnExpression() {
        return mustReturnExpression;
    }

    public void setMustReturnExpression(boolean mustReturnExpression) {
        this.mustReturnExpression = mustReturnExpression;
    }

    public Type getExpectedReturnType() {
        return expectedReturnType;
    }

    public void setExpectedReturnType(Type expectedReturnType) {
        this.expectedReturnType = expectedReturnType;
    }

    public int isUnreachableStatement() {
        return unreachableStatement;
    }

    public void setUnreachableStatement() {
        this.unreachableStatement++;
    }

    public void unsetUnreachableStatement() {
        this.unreachableStatement--;
    }

    public void resetUnreachableStatement() {
        this.unreachableStatement = 0;
    }

    public boolean isLeftValueAsLocation() {
        return leftValueAsLocation;
    }

    public void setLeftValueAsLocation(boolean leftValueAsLocation) {
        this.leftValueAsLocation = leftValueAsLocation;
    }

    public boolean isInsidePrint() {
        return insidePrint;
    }

    public void setInsidePrint(boolean insidePrint) {
        this.insidePrint = insidePrint;
    }

    public boolean isInsideRead() {
        return insideRead;
    }

    public void setInsideRead(boolean insideRead) {
        this.insideRead = insideRead;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Second version, Good to go">
    public void saveContext() {
        this.contextStack.saveContext();
    }
    
    public void loadContext() {
        this.contextStack.loadContext();
    }
    
    public Context currentContext() {
        return this.contextStack.peek();
    }
    
    public void registerContext(Context context) {
        this.contextStack.push(context);
    }
    
    public void unregisterContext() {
        this.contextStack.pop().setParent(null);
    }
    
    public void mustMergeWithTempContext(boolean mustMerge) {
        this.mustMergeWithTemp = mustMerge;
    }
    
    public boolean mustMergeWithTempContext() {
        return this.mustMergeWithTemp;
    }
    
    public void setTmpContext(Context tmp) {
        this.tmp = tmp;
    }
    
    public Context getTmpContext() {
        return this.tmp;
    }
    
    private ArrayList<RE_Variable> global = new ArrayList<RE_Variable>();    
    
    public void addVariableRE(RE_Variable newVar) {
        this.global.add(newVar);
    }
    
    public RE_Variable getVariableRE(int index) {
        return global.get(index);
    }
    
    public RE_Variable[] getGlobal() {
        RE_Variable[] newArr = new RE_Variable[global.size()];
        for (int i = 0; i < global.size(); i++) {
            RE_Variable rE_Variable = global.get(i);
            newArr[i] = rE_Variable;
        }
        return newArr;
    }
    
    public int findInRE(String name) {
        int res = currentContext().findInRE(name);
        if (res < 0) {
            for (int i = 0; i < global.size(); i++) {
                RE_Variable object = global.get(i);
                if (object.getName().equals(name)) {
                    return i;
                }
            }
        }
        return res;
    }
    
    public int getContextId(String name) {
        return currentContext().getContextId(name);
    }
    
    private ArrayList<Procedure> procedures = new ArrayList<Procedure>();
    
    public void addProcedure(Procedure p) {
        procedures.add(p);
    }
    
    public Procedure getProcedure(int index) {
        return procedures.get(index);
    }
    
    public Procedure[] getProcedures() {
        Procedure[] procs = new Procedure[procedures.size()];
        for (int i = 0; i < procedures.size(); i++) {
            Procedure procedure = procedures.get(i);
            procs[i] = procedure;
        }
        return procs;
    }
    
    public int findProcedureIndex(String name) {
        for (int i = 0; i < procedures.size(); i++) {
            Procedure object = procedures.get(i);
            if (object.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
    
    public Procedure findProcedure(String name) {
        for (int i = 0; i < procedures.size(); i++) {
            Procedure object = procedures.get(i);
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }
    
    int contextId = 1;
    public int generateContextId() {
        return contextId++;
    }
    //</editor-fold>

    
    
}
