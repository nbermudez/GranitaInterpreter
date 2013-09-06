/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_And extends D_BinaryExpression {

    public D_And(D_Expression left, D_Expression right) {
        super(left, right);
    }

    @Override
    public Object evaluate() {
        Boolean l = (Boolean) left.evaluate();
        Boolean r = (Boolean) right.evaluate();
        
        return l && r;
    }
    
}
