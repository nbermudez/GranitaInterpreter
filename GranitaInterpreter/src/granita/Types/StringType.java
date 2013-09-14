/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class StringType extends Type {
    
    @Override
    public boolean equivalent(Type t) {
        return t instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public Type getCopy() {
        return new StringType();
    }
    
}
