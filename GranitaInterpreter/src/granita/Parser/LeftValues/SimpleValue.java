/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

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
            return ErrorHandler.handle("undefined variable " + id + ": line "
                    + this.getLine());
        } else {
            return ((Variable) val).getType();
        }
    }
}
