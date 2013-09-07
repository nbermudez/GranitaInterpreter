/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.Parser.Expressions.Expression;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleVariable extends Variable {
    Expression value, previousValue;

    public SimpleVariable(Type type, Expression value) {
        super(type);
        this.value = value;
        if (value != null) {
            this.setInitialized(true);
        }
        this.previousValue = value;
    }
    
    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
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
