/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Semantic.SymbolTable.ArrayVariable;
import granita.Semantic.SymbolTable.SimpleVariable;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
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

    public SimpleValue(int scopeId, int line) {
        super(scopeId, line);
    }

    public SimpleValue(String id, int scopeId, int line) {
        super(scopeId, line);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
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
    public Object evaluate() throws GranitaException {
        SimpleVariable val = (SimpleVariable) Interpreter.getInstance().getVariable(id);
        Type t = val.getType();
        return t.getValue();
    }

    @Override
    public Type getLocation() {
        Variable var = Interpreter.getInstance().getVariable(id);
        return var.getType();
    }
}
