/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Semantic.Types.IntType;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_LitInt extends D_Expression {

    IntResult value;
    
    public D_LitInt(int value) {
        this.expressionType = new IntType();
        this.value = new IntResult(value);
    }
    
    public int getValue() {
        return value.getValue();
    }

    @Override
    public Result eval() {
        return value;
    }
    
}
