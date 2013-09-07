/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BoolType extends Type{
    
    Boolean value;

    public BoolType() {
        this.value = false;
    }

    public BoolType(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
    
    @Override
    public boolean equivalent(Type t) {
        return t instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
    
    public static String text(){
        return "bool";
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Boolean){
            this.value = (Boolean) value;
        }
    }

    @Override
    public Type getCopy() {
        return new BoolType(value);
    }
}
