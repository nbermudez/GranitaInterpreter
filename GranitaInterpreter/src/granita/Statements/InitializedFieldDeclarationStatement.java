/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Expressions.Expression;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class InitializedFieldDeclarationStatement extends Statement {
    Expression initValue;
    String fieldName, type;

    public InitializedFieldDeclarationStatement(String type, 
            String fieldName, Expression initValue, int line) {
        super(line);
        this.initValue = initValue;
        this.fieldName = fieldName;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + fieldName + " = " + initValue.toString();
    }

    @Override
    public void validateSemantics() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
