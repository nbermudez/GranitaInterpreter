/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.DataLayout.ContextStack;
import granita.Parser.Statements.BlockStatement;
import granita.Types.Type;

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
    private BlockStatement currentBlock = null;
    private ContextStack contextStack;
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

    public BlockStatement getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(BlockStatement currentBlock) {
        this.currentBlock = currentBlock;
    }
    
    public void saveContext() {
        this.contextStack.saveContext();
    }
    
    public void loadContext() {
        this.contextStack.loadContext();
    }
}
