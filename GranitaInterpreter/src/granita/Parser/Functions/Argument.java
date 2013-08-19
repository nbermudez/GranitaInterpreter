/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Argument extends Expression {

    Expression value;
    int scopeId;

    public Argument(int scopeId, int line) {
        super(line);
        this.scopeId = scopeId;
    }

    public Argument(Expression value, int scopeId, int line) {
        super(line);
        this.value = value;
        this.scopeId = scopeId;
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
