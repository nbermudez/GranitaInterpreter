/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BoolVariable extends Variable {

    public BoolVariable(Type type) {
        super(type);
    }

    @Override
    public Variable getCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
