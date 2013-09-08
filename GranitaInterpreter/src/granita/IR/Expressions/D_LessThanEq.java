/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Types.BoolType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_LessThanEq extends D_BinaryExpression {

    public D_LessThanEq(D_Expression left, D_Expression right) {
        super(left, right);
        this.expressionType = new BoolType();
    }

    @Override
    public Object evaluate() {
        Integer l = (Integer) left.evaluate();
        Integer r = (Integer) right.evaluate();
        
        return l <= r;
    }
    
}
