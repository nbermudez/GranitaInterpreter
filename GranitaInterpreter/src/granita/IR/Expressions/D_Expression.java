/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.Result;
import granita.Semantic.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class D_Expression {
    Type expressionType;

    public Type getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Type expressionType) {
        this.expressionType = expressionType;
    }
    
    public abstract Result eval();
}
