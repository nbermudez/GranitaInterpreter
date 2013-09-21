/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.FieldItems;

import granita.Parser.Statements.Statement;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public abstract class Field extends Statement {
    String fieldName;

    public Field(String fieldName, int line) {
        super(line);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
}
