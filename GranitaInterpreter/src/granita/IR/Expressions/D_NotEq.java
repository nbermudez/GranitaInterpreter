/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

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
    }

    @Override
    public Object evaluate() {
        Object l =  left.evaluate();
        Object r = right.evaluate();
        
        return !l.equals(r);
    }
    
}
