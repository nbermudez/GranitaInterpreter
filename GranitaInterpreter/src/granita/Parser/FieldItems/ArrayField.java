/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.FieldItems;

import granita.IR.Expressions.D_LitInt;
import granita.Parser.Expressions.LitInt;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayField extends Field {
    D_LitInt size;

    public ArrayField(String fieldName, D_LitInt size, int line) {
        super(fieldName, line);
        this.fieldName = fieldName;
        this.size = size;
    }

    public D_LitInt getSize() {
        return size;
    }

    public void setSize(D_LitInt size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return fieldName + "[" + size.toString() + "]";
    }

    @Override
    public void validateSemantics() throws GranitaException {
        if (size.getValue() <= 0) {
            ErrorHandler.handle("array size must be greater than zero: line " 
                    + this.getLine());
        }
    }
    
}
