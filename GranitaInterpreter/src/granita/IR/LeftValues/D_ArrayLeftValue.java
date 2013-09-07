/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.IR.Expressions.D_Expression;
import granita.DataLayout.ArrayVariable;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_ArrayLeftValue extends D_LeftValue {
    D_Expression index;

    public D_ArrayLeftValue(D_Expression index, String identifier) {
        super(identifier);
        this.index = index;
        ArrayVariable value = (ArrayVariable) Interpreter.getInstance().getVariable(identifier);
        this.setExpressionType(value.getType());
    }
    
    @Override
    public Object evaluate() {
        ArrayVariable value = (ArrayVariable) Interpreter.getInstance().getVariable(identifier);
        Integer calculatedIndex = (Integer) index.evaluate();
        if (calculatedIndex >= value.getSize().getValue()) {
            try {
                ErrorHandler.handleFatalError("index out of bound in variable '"
                        + identifier + "'");
            } catch (GranitaException ex) {
                return null;
            }
        }
        Type[] items = value.getItems();
        Type item = items[calculatedIndex];
        return item.getValue();
    }
    
}
