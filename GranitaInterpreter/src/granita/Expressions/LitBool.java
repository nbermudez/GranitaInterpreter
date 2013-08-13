/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class LitBool extends Expression {
    boolean value;
    
    public LitBool(boolean value, int line){
        this.value = value;
        this.line = line;
    }
    
    public LitBool(String value, int line){
        this.value = Boolean.parseBoolean(value);
        this.line = line;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
    
    
}
