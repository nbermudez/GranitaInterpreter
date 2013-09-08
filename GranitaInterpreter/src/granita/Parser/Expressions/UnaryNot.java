/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.Types.BoolType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class UnaryNot extends Expression {

    Expression value;

    public UnaryNot(Expression value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + "!" + value.toString() + ")";
    }

    @Override
    public D_Expression getIR() {
        D_Expression ret = value.getIR();
        Type tvalue = ret.getExpressionType();
        if (tvalue == null) {
            return null;
        }

        if (tvalue instanceof BoolType) {
            return ret;
        } else {
            ErrorHandler.handle("'not' cannot be applied to "
                    + tvalue.toString() + ": line " + line);
        }
        return null;
    }
}
