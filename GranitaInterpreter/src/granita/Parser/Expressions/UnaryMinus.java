/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.Types.IntType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class UnaryMinus extends Expression {

    Expression value;

    public UnaryMinus(Expression value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + "-" + value.toString() + ")";
    }

    @Override
    public D_Expression getIR() {
        D_Expression ret = value.getIR();
        Type tvalue = ret.getExpressionType();
        if (tvalue == null) {
            ErrorHandler.handle("undefined variable " + value.toString()
                    + ": line " + line);
        }

        if (tvalue instanceof IntType) {
            return ret;
        } else {
            ErrorHandler.handle("Operator - cannot be applied to "
                    + tvalue.toString() + ": line " + line);
            return null;
        }
    }
}
