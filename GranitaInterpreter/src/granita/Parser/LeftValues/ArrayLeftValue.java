/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.IR.Expressions.D_Expression;
import granita.IR.LeftValues.D_ArrayLeftValue;
import granita.IR.LeftValues.D_LeftValue;
import granita.Misc.ErrorHandler;
import granita.Parser.Expressions.Expression;
import granita.Semantic.DataLayout.ArrayVariable;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granita.Semantics.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class ArrayLeftValue extends LeftValue {

    String id;
    Expression index;

    public ArrayLeftValue(int line) {
        super(line);
    }

    public ArrayLeftValue(String id, Expression index, int line) {
        super(line);
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

    @Override
    public String toString() {
        return id + "[" + index.toString() + "]";
    }

    @Override
    public D_LeftValue getIR() {
        SymbolTableEntry value = SemanticUtils.getInstance().currentContext().find(id);
        int position = SemanticUtils.getInstance().findInRE(id);
        int contextId = SemanticUtils.getInstance().getContextId(id);
        if (value == null) {
            ErrorHandler.handle("undefined variable '" + id + "' in line "
                    + this.getLine());
            return null;
        } else {
            if (!(value instanceof ArrayVariable)) {
                ErrorHandler.handle("variable '" + id + "' is not an array: line "
                        + this.getLine());
                return null;
            } else {
                D_Expression arrIndex = this.index.getIR();
                if (arrIndex != null) {
                    Type it = arrIndex.getExpressionType();

                    if (!it.equivalent(new IntType())) {
                        ErrorHandler.handle("array index must be int: line "
                                + this.getLine());
                        return null;
                    }
                }
                Type t = ((ArrayVariable)value).getType();
                return new D_ArrayLeftValue(t, arrIndex, id, position, contextId);
            }
        }
    }
}
