/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_UnaryMinus extends D_Expression {
    D_Expression value;

    public D_UnaryMinus(D_Expression value) {
        this.value = value;
    }
    
    @Override
    public Object evaluate() {
        Integer r = (Integer) value.evaluate();
        return -r;
    }
    
}
