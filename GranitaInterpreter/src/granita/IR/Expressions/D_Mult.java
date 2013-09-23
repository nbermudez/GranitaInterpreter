/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_Mult extends D_BinaryExpression {

    public D_Mult(D_Expression left, D_Expression right) {
        super(left, right);
    }
    
    @Override
    public Result eval() {
        IntResult l = (IntResult) left.eval();
        
        if (l.getValue() == 0) {
            return new IntResult(0);
        }
        
        IntResult r = (IntResult) right.eval();
        
        return new IntResult(l.getValue() * r.getValue());
    }
}
