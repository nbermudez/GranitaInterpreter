/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.FieldItems;

import granita.Statements.Statement;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Field extends Statement {
    String fieldName;

    public Field(String fieldName, int line) {
        super(line);
        this.fieldName = fieldName;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
