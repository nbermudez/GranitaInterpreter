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
    
    Integer value;   

    public IntType() {
    }

    public IntType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
        if (value instanceof Integer){
            this.value = (Integer) value;
        }
    }

    @Override
    public Type getCopy() {
        return new IntType(value);
    }
}
