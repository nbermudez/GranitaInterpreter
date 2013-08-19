/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IntType extends Type{
    
    IntResult value;   

    public IntResult getValue() {
        return value;
    }

    public void setValue(IntResult value) {
        this.value = value;
    }

    @Override
    public boolean equivalent(Type t) {
        return t instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
    
    public static String text(){
        return "int";
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof IntResult){
            this.value = (IntResult) value;
        }
    }
}
