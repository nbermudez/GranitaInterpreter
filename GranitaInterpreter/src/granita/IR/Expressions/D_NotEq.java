/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.BoolResult;
import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Types.BoolType;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_NotEq extends D_BinaryExpression {
    Type sideType;

    public D_NotEq(Type sideType, D_Expression left, D_Expression right) {
        super(left, right);
        this.sideType = sideType;
        this.expressionType = new BoolType();
    }

    @Override
    public Object evaluate() {
        Object l =  left.evaluate();
        Object r = right.evaluate();
        
        return !l.equals(r);
    }

    @Override
    public Result eval() {
        Result l = left.eval();
        Result r = right.eval();
        if (l instanceof IntResult && r instanceof IntResult) {
            return new BoolResult(((IntResult) l).getValue().intValue() != ((IntResult) r).getValue().intValue());
        } else if (l instanceof BoolResult && r instanceof BoolResult) {
            return new BoolResult(((BoolResult) l).getValue().booleanValue() != ((BoolResult) r).getValue().booleanValue());
        } else {
            return new BoolResult(false);
        }
    }
    
}
