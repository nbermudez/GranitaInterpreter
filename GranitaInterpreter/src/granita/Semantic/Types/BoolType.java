/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class BoolType extends Type{
    
    public BoolType() {
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
    public Type getCopy() {
        return new BoolType();
    }
}
