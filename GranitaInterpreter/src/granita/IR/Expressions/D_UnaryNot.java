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
public class D_UnaryNot extends D_Expression{
    D_Expression value;

    public D_UnaryNot(D_Expression value) {
        this.value = value;
        this.expressionType = new BoolType();
    }
    
    @Override
    public Object evaluate() {
        Boolean r = (Boolean) value.evaluate();
        return !r;
    }
    
}
