/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class Type {
    
    public abstract boolean equivalent(Type t);
    
    public abstract void setValue(Object value);
    public abstract Object getValue();
    public abstract Type getCopy();
}
