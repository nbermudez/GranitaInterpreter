/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.IR.Expressions.D_Expression;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class D_LeftValue extends D_Expression {
    String identifier;

    public D_LeftValue(String identifier) {
        this.identifier = identifier;
    }
}
