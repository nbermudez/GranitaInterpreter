/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.IR.Expressions.D_LitBool;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class LitBool extends Expression {
    boolean value;
    
    public LitBool(boolean value, int line){
        super(line);
        this.value = value;
    }
    
    public LitBool(String value, int line){
        super(line);
        this.value = Boolean.parseBoolean(value);
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public D_Expression getIR() {
        return new D_LitBool(value);
    }
}
