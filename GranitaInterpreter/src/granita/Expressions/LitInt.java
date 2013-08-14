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
        super(line);
        this.value = value;
    }
    
    public LitInt(String value, int line){
        super(line);
        if (value.contains("X") || value.contains("x")){
            this.value = Integer.parseInt(value.substring(value.replace("X", "x").indexOf("x")+1), 16);
        }else{
            this.value = Integer.parseInt(value);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }    
    
}
