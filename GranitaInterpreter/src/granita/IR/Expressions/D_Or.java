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
public class D_Or extends D_BinaryExpression {

    public D_Or(D_Expression left, D_Expression right) {
        super(left, right);
        this.expressionType = new BoolType();
    }

    @Override
    public Object evaluate() {
        Boolean l = (Boolean) left.evaluate();
        Boolean r = (Boolean) right.evaluate();
        
        return l || r;
    }
    
}
