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
public class D_Or extends D_BinaryExpression {

    public D_Or(D_Expression left, D_Expression right) {
        super(left, right);
        this.expressionType = new BoolType();
    }
    
    @Override
    public Result eval() {
        BoolResult l = (BoolResult) left.eval();
        
        if (l.getValue()) {
            return new BoolResult(Boolean.TRUE);
        }
        
        //no need to check if left side is already true
        BoolResult r = (BoolResult) right.eval();
        
        return new BoolResult(l.getValue() || r.getValue());
    }
}
