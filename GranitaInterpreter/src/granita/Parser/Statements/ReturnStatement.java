/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
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
                throw new GranitaException("return value cannot be void in line " + line);
            }
        }
    }
    
    
}
