/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_LitInt;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
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
        }else if (value.length()>2 && !Character.isDigit(value.charAt(1))){
            this.value = value.charAt(1);
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
    
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public D_LitInt getIR() {
        return new D_LitInt(value);
    }
}
