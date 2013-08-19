/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BoolType extends Type{
    
    BoolResult value;

    public BoolResult getValue() {
        return value;
    }

    public void setValue(BoolResult value) {
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
        if (value instanceof BoolResult){
            this.value = (BoolResult) value;
        }
    }
}
