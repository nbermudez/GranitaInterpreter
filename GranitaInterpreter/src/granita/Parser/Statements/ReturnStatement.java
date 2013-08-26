/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReturnStatement extends Statement {
    boolean isInsideFunction;
    Expression returnExpression;

    public ReturnStatement(boolean isInsideFunction, Expression returnExpression, int line) {
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

    @Override
    public String toString() {
        String ret = "return";
        if (returnExpression != null){
            ret += " " + returnExpression.toString();
        }
        return ret;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        if (returnExpression != null){
            Type ret = returnExpression.validateSemantics();
            if (ret instanceof VoidType){
                ErrorHandler.handle("return value cannot be void: line " + line);
            }
        }
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        if (returnExpression == null) {
            return null;
        }
        return returnExpression.validateSemantics();
    }
    
    
}
