/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.IR.Functions.D_Argument;
import granita.Parser.Expressions.Expression;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Argument extends Expression {

    Expression value;
    public Argument(int line) {
        super(line);
    }

    public Argument(Expression value, int line) {
        super(line);
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public D_Argument getIR() {
        return new D_Argument(value.getIR());
    }
}
