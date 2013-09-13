/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Sub extends D_BinaryExpression {

    public D_Sub(D_Expression left, D_Expression right) {
        super(left, right);
    }

    @Override
    public Object evaluate() {
        Integer l = (Integer) left.evaluate();
        Integer r = (Integer) right.evaluate();

        return l - r;
    }
    
    @Override
    public Result eval() {
        IntResult l = (IntResult) left.eval();
        IntResult r = (IntResult) right.eval();
        
        return new IntResult(l.getValue() - r.getValue());
    }
}
