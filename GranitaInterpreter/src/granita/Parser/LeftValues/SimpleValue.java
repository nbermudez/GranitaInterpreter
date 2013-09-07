/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.IR.Expressions.D_Expression;
import granita.IR.LeftValues.D_LeftValue;
import granita.IR.LeftValues.D_SimpleValue;
import granita.DataLayout.ArrayVariable;
import granita.DataLayout.SimpleVariable;
import granita.DataLayout.Variable;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleValue extends LeftValue {

    String id;

    public SimpleValue(int line) {
        super(line);
    }

    public SimpleValue(String id, int line) {
        super(line);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Type validateSemantics() throws GranitaException {
        Variable val = SemanticUtils.getInstance().getCurrentBlock().getVariable(id);
        if (val == null) {
            return ErrorHandler.handle("undefined variable '" + id + "': line "
                    + this.getLine());
        } else {
            if (SemanticUtils.getInstance().isInsidePrint() 
                    && val instanceof ArrayVariable) {
                return ErrorHandler.handle("print can't be applied to an array "
                        + "variable: line " + this.getLine());
            }
            if (SemanticUtils.getInstance().isInsideRead() 
                    && val instanceof ArrayVariable) {
                return ErrorHandler.handle("read can't be applied to an array "
                        + "variable: line " + this.getLine());
            }
            if (!SemanticUtils.getInstance().isLeftValueAsLocation() 
                    && !val.isInitialized()) {
                return ErrorHandler.handle("variable '" + id + "' "
                        + "must be initialized before use: line "
                        + this.getLine());
            }
            return ((SimpleVariable) val).getType();
        }
    }

    @Override
    public void initializeVariable() {
        Variable val = SemanticUtils.getInstance().getCurrentBlock().getVariable(id);
        if (val != null && val instanceof SimpleVariable) {
            val.setInitialized(true);
        }
    }

    @Override
    public Type getLocation() {
        Variable var = Interpreter.getInstance().getVariable(id);
        return var.getType();
    }

    @Override
    public D_LeftValue getIR() {
        Variable val = SemanticUtils.getInstance().getCurrentBlock().getVariable(id);
        if (val == null) {
            ErrorHandler.handle("undefined variable '" + id + "': line "
                    + this.getLine());
            return null;
        } else {
            if (SemanticUtils.getInstance().isInsidePrint() 
                    && val instanceof ArrayVariable) {
                ErrorHandler.handle("print can't be applied to an array "
                        + "variable: line " + this.getLine());
                return null;
            }
            if (SemanticUtils.getInstance().isInsideRead() 
                    && val instanceof ArrayVariable) {
                ErrorHandler.handle("read can't be applied to an array "
                        + "variable: line " + this.getLine());
                return null;
            }
            if (!SemanticUtils.getInstance().isLeftValueAsLocation() 
                    && !val.isInitialized()) {
                ErrorHandler.handle("variable '" + id + "' "
                        + "must be initialized before use: line "
                        + this.getLine());
                return null;
            }
            return new D_SimpleValue(id);
        }
    }
}
