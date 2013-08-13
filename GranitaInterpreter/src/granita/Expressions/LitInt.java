/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class LitInt extends Expression {
    int value;
    
    public LitInt(int value, int line){
        this.value = value;
        this.line = line;
    }
    
    public LitInt(String value, int line){
        this.value = Integer.parseInt(value);
        this.line = line;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }    
    
}
