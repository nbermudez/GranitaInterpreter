/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Eq extends BinaryExpression {

    public Eq(Expression left, Expression right, int line) {
        super(left, right, line);
    }
    
    @Override
    public String toString() {
        return left.toString() + " == " + right.toString();
    }
}
