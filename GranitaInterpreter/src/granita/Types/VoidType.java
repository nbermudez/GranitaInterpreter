/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class VoidType extends Type{

    @Override
    public boolean equivalent(Type t) {
        return t instanceof VoidType;
    }
    
}
