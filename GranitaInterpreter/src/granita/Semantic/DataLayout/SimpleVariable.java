/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.DataLayout;

import granita.Semantic.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleVariable extends Variable {
    
    public SimpleVariable(Type type, boolean initialized) {
        super(type);
        this.setInitialized(initialized);
    }

    @Override
    public SimpleVariable getCopy() {
        SimpleVariable v = new SimpleVariable(type.getCopy(), isInitialized());
        v.setVarName(varName);
        v.setInitialized(this.isInitialized());
        return v;
    }
}
