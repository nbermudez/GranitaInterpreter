/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Functions;

import granita.Expressions.Expression;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Argument extends Expression{
    Expression value;
    
    public Argument(int line) {
        super(line);
    }

    public Argument(Expression value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    
    @Override
    public Type validateSemantics() throws GranitaException {
        return value.validateSemantics();
    }
}
