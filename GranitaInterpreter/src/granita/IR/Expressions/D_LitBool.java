/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_LitBool extends D_Expression {
    boolean value;

    public D_LitBool(boolean value) {
        this.value = value;
    }
    
    @Override
    public Object evaluate() {
        return value;
    }
    
}
