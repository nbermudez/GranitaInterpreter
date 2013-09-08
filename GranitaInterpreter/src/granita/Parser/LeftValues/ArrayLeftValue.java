/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.DataLayout.ArrayVariable;
import granita.DataLayout.Variable;
import granita.IR.Expressions.D_Expression;
import granita.IR.LeftValues.D_ArrayLeftValue;
import granita.IR.LeftValues.D_LeftValue;
import granita.Parser.Expressions.Expression;
import granita.SymbolTable.SymbolTableEntry;
import granita.Types.IntType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.Interpreter;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayLeftValue extends LeftValue {

    String id;
    Expression index;
    int calculatedIndex = -1;

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
    public void initializeVariable() {
    }

    @Override
    public Type getLocation() {
        Variable var = Interpreter.getInstance().getVariable(id);
        ArrayVariable arrVar = (ArrayVariable) var;
        //calculatedIndex = (Integer) index.evaluate();
        return arrVar.getItems()[calculatedIndex];
    }

    @Override
    public D_LeftValue getIR() {
        SymbolTableEntry value = SemanticUtils.getInstance().currentContext().find(id);
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
                ArrayVariable array = (ArrayVariable) value;
                D_Expression arrIndex = this.index.getIR();
                Type it = arrIndex.getExpressionType();

                if (!it.equivalent(new IntType())) {
                    ErrorHandler.handle("array index must be int: line "
                            + this.getLine());
                    return null;
                }
                array.getType().setValue(it.getValue());
                return new D_ArrayLeftValue(arrIndex, id);
            }
        }
    }
}
