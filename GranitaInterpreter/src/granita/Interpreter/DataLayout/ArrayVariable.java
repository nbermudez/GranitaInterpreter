/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayVariable extends RE_Variable {
    private RE_Variable[] items;
    
    public ArrayVariable(Type type, String name, int size) {
        super(name);
        if (type == RE_Variable.Type.BOOL_VARIABLE) {
            items = new BoolVariable[size];
        } else {
            items = new IntVariable[size];
        }
    }

    public RE_Variable[] getItems() {
        return items;
    }

    public void setItems(RE_Variable[] items) {
        this.items = items;
    }
    
    public RE_Variable getItem(int index) throws GranitaException {
        if (index > items.length) {
            ErrorHandler.handleFatalError("index out of bound in " + name);
            return null; //unreachable but needed
        } else {
            return items[index];
        }
    }
    
}
