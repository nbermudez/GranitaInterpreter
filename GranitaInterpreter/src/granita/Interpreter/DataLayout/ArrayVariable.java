/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

import granita.Interpreter.Results.BoolResult;
import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Misc.ErrorHandler;
import granita.Misc.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayVariable extends RE_Variable {

    private RE_Variable[] items;
    private Type type;

    public ArrayVariable(Type type, String name, int size) {
        super(name);
        this.type = type;
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

    public RE_Variable getItem(int index) {
        if (index > items.length || index < 0) {
            try {
                ErrorHandler.handleFatalError("index out of bound in array '" + name + "'");
            } catch (GranitaException ex) {
            }
            return null; //unreachable but needed
        } else {
            return items[index];
        }
    }

    public void setItemValue(int index, Result value) {
        if (value instanceof IntResult) {
            IntResult res = (IntResult) value;
            this.items[index] = new IntVariable("", res.getValue());
        } else {
            BoolResult res = (BoolResult) value;
            this.items[index] = new BoolVariable("", res.getValue());
        }
    }

    @Override
    public RE_Variable getCopy() {
        ArrayVariable copy = new ArrayVariable(type, name, items.length);
        for (int i = 0; i < items.length; i++) {
            RE_Variable rE_Variable = items[i];
            if (rE_Variable != null) {
                copy.items[i] = rE_Variable.getCopy();
            }
        }

        return copy;
    }
}
