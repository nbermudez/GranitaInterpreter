/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.IR.Expressions.D_LitString;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class LitString extends Expression {
    String value;
    
    public LitString(String value, int line){
        super(line);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }    
    
    @Override
    public String toString() {
        return "\"" + value.replace("\n", "\\n").replace("\t", "\\t") + "\"";
    }

    @Override
    public D_Expression getIR() {
        return new D_LitString(value);
    }
}
