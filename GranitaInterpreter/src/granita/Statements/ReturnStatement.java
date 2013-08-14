/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granitainterpreter.AstNode;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReturnStatement extends Statement {
    boolean isInsideFunction;
    AstNode returnExpression;

    public ReturnStatement(boolean isInsideFunction, AstNode returnExpression, int line) {
        super(line);
        this.isInsideFunction = isInsideFunction;
        this.returnExpression = returnExpression;
    }

    public boolean isIsInsideFunction() {
        return isInsideFunction;
    }

    public void setIsInsideFunction(boolean isInsideFunction) {
        this.isInsideFunction = isInsideFunction;
    }
    
    
}
