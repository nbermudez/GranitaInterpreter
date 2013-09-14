/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IntType extends Type{

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
    public Type getCopy() {
        return new IntType();
    }
}
