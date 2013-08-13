/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Values;

import granita.Expressions.Expression;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Var extends Value{
    Type type;
    Expression value;

    public Var(Type type, Expression value) {
        this.type = type;
        this.value = value;
    }

    public Type getTipo() {
        return type;
    }

    public void setTipo(Type tipo) {
        this.type = tipo;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
