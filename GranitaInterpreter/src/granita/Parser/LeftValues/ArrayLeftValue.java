/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Parser.Expressions.Expression;
import granita.Parser.Expressions.LitInt;
import granita.Semantic.SymbolTable.ArrayVariable;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayLeftValue extends LeftValue {

    String id;
    Expression index;

    public ArrayLeftValue(int scopeId, int line) {
        super(scopeId, line);
    }

    public ArrayLeftValue(String id, Expression index, int scopeId, int line) {
        super(scopeId, line);
        this.id = id;
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }


    @Override
    public Type validateSemantics() throws GranitaException {
        SymbolTableValue value = SymbolTableTree.getInstance().lookupFromCurrent(id);
        if (value == null) {
            return ErrorHandler.handle("undefined variable '" + id + "' in line "
                    + this.getLine());
        } else {
            if (!(value instanceof ArrayVariable)) {
                return ErrorHandler.handle("variable '" + id + "' is not an array: line "
                        + this.getLine());
            } else {
                ArrayVariable array = (ArrayVariable) value;
                Type it = this.index.validateSemantics();

                if (!it.equivalent(new IntType())) {
                    return ErrorHandler.handle("array index must be int: line "
                            + this.getLine());
                }
                array.getType().setValue(it.getValue());
                return array.getType();
            }
        }
    }

    @Override
    public String toString() {
        return id + "[" + index.toString() + "]";
    }
}
