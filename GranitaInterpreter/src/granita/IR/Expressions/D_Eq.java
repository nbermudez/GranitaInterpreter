/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Types.BoolType;
import granita.Types.IntType;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Eq extends D_BinaryExpression {
    Type sideType;

    public D_Eq(Type sideType, D_Expression left, D_Expression right) {
        super(left, right);
        this.sideType = sideType;
    }
    
    @Override
    public Object evaluate() {
        if (sideType instanceof IntType) {
            Integer l = (Integer) left.evaluate();
            Integer r = (Integer) right.evaluate();
            return l.intValue() == r.intValue();
        } else if (sideType instanceof BoolType){
            Boolean l = (Boolean) left.evaluate();
            Boolean r = (Boolean) right.evaluate();
            return l.booleanValue() == r.booleanValue();
        } else {
            return null;
        }
    }
    
}
