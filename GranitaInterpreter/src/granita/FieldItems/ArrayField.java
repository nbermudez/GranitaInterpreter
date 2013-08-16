/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.FieldItems;

import granita.Expressions.LitInt;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayField extends Field {
    LitInt size;

    public ArrayField(String fieldName, LitInt size, int line) {
        super(fieldName, line);
        this.fieldName = fieldName;
        this.size = size;
    }

    @Override
    public String toString() {
        return fieldName + "[" + size.toString() + "]";
    }
    
}
