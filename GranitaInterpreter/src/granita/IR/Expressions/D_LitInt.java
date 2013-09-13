/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Types.IntType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_LitInt extends D_Expression {

    public D_LitInt(int value) {
        this.expressionType = new IntType(value);
    }
    
    public int getValue() {
        return (Integer) this.expressionType.getValue();
    }
    
    @Override
    public Object evaluate() {
        return this.expressionType.getValue();
    }

    @Override
    public Result eval() {
        return new IntResult((Integer) this.expressionType.getValue());
    }
    
}
