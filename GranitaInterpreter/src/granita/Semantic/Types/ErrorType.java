/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class ErrorType extends Type{

    @Override
    public boolean equivalent(Type t) {
        return (t instanceof ErrorType);
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
