/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.BoolResult;
import granita.Interpreter.Results.Result;
import granita.Types.BoolType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_LitBool extends D_Expression {
    
    public D_LitBool(boolean value) {
        this.expressionType = new BoolType(value);
    }
    
    @Override
    public Object evaluate() {
        return this.expressionType.getValue();
    }

    @Override
    public Result eval() {
        return new BoolResult((Boolean)this.expressionType.getValue());
    }
    
}
