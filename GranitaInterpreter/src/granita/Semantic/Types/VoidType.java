/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class VoidType extends Type{

    @Override
    public boolean equivalent(Type t) {
        return t instanceof VoidType;
    }

    @Override
    public String toString() {
        return "void";
    }
    
    public static String text(){
        return "void";
    }

    @Override
    public void setValue(Object value) {
        throw new UnsupportedOperationException("Void type can't have a value");
    }
}
