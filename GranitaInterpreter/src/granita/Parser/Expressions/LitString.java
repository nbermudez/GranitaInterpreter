/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.Types.StringType;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class LitString extends Expression {
    String value;
    
    public LitString(String value, int line){
        super(line);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }    
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    public Type validateSemantics() throws GranitaException {
        return new StringType();
    }
}
