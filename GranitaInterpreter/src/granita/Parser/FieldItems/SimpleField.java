/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.FieldItems;


/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleField extends Field {

    public SimpleField(String fieldName, int line) {
        super(fieldName, line);
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return fieldName;
    }
    
}
