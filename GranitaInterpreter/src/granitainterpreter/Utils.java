/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.Semantic.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Utils {
    private boolean mustReturnExpression = false;
    private boolean errored = false;
    private int unreachableStatement = 0;
    private Type expectedReturnType = null;
    private boolean leftValueAsLocation = false;
    private Utils() {
    }
    
    public static Utils getInstance() {
        return UtilsHolder.INSTANCE;
    }
    
    private static class UtilsHolder {

        private static final Utils INSTANCE = new Utils();
    }

    public boolean mustReturnExpression() {
        return mustReturnExpression;
    }

    public void setMustReturnExpression(boolean mustReturnExpression) {
        this.mustReturnExpression = mustReturnExpression;
    }

    public boolean isErrored() {
        errored = !errored;
        return !errored;
    }

    public void setErrored() {
        this.errored = true;
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
}
