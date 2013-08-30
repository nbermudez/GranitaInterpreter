/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Semantic.SymbolTable.ArrayVariable;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Utils;

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
        SymbolTableValue val = SymbolTableTree.getInstance().lookupFromCurrent(id);
        if (val == null) {
            return ErrorHandler.handle("undefined variable '" + id + "': line "
                    + this.getLine());
        } else {
            if (Utils.getInstance().isInsidePrint() 
                    && val instanceof ArrayVariable) {
                return ErrorHandler.handle("print can't be applied to an array "
                        + "variable: line " + this.getLine());
            }
            if (Utils.getInstance().isInsideRead() 
                    && val instanceof ArrayVariable) {
                return ErrorHandler.handle("read can't be applied to an array "
                        + "variable: line " + this.getLine());
            }
            if (!Utils.getInstance().isLeftValueAsLocation() 
                    && !val.isInitialized()) {
                return ErrorHandler.handle("variable '" + id + "' "
                        + "must be initialized before use: line "
                        + this.getLine());
            }
            return ((Variable) val).getType();
        }
    }

    @Override
    public void initializeVariable() {
        SymbolTableValue val = SymbolTableTree.getInstance().lookupFromCurrent(id);
        if (val != null && val instanceof Variable) {
            val.setInitialized(true);
        }
    }

    @Override
    public Object evaluate() throws GranitaException {
        Variable val = (Variable) SymbolTableTree.getInstance().lookupFromCurrent(id);
        return val.getType().getValue();
    }
}
