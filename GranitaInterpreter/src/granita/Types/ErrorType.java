/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ErrorType extends Type{

    @Override
    public boolean equivalent(Type t) {
        return (t instanceof ErrorType);
    }

    @Override
    public void setValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Type getCopy() {
        return this;
    }
    
}
