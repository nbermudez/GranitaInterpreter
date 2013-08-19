/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class InitializedFieldDeclarationStatement extends Statement {
    Expression initValue;
    String fieldName;
    Type type;

    public InitializedFieldDeclarationStatement(Type type, 
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
        System.out.println("Not supported yet.");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public Expression getInitValue() {
        return initValue;
    }

    public void setInitValue(Expression initValue) {
        this.initValue = initValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
}
