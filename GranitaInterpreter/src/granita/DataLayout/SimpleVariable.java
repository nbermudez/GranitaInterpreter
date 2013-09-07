/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.IR.Expressions.D_Expression;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleVariable extends Variable {
    D_Expression value, previousValue;

    public SimpleVariable(Type type, D_Expression value) {
        super(type);
        this.value = value;
        if (value != null) {
            this.setInitialized(true);
        }
        this.previousValue = value;
    }
    
    public D_Expression getValue() {
        return value;
    }

    public void setValue(D_Expression value) {
        this.value = value;
    }

    @Override
    public SimpleVariable getCopy() {
        SimpleVariable v = new SimpleVariable(type.getCopy(), value);
        v.setVarName(varName);
        v.setInitialized(this.isInitialized());
        return v;
    }
}
