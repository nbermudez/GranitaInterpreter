/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_LitString extends D_Expression {
    String value;

    public D_LitString(String value) {
        this.value = value;
    }
    
    @Override
    public Object evaluate() {
        return value;
    }
    
}
