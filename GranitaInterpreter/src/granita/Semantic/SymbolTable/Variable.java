/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Variable extends SymbolTableValue {
    Type type;
    Expression value;

    public Variable(Type type, Expression value) {
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
