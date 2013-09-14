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
public class D_UnaryMinus extends D_Expression {
    D_Expression value;

    public D_UnaryMinus(D_Expression value) {
        this.value = value;
        this.expressionType = new IntType();
    }
    
    @Override
    public Object evaluate() {
        Integer r = (Integer) value.evaluate();
        return -r;
    }

    @Override
    public Result eval() {
        return new IntResult(-1 * (Integer)value.eval().getValue());
    }
    
}
