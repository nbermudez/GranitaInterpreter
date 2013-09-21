/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.BoolResult;
import granita.Interpreter.Results.Result;
import granita.Semantic.Types.BoolType;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_LitBool extends D_Expression {
    
    BoolResult value;
    
    public D_LitBool(boolean value) {
        this.expressionType = new BoolType();
        this.value = new BoolResult(value);
    }

    @Override
    public Result eval() {
        return value;
    }
    
}
