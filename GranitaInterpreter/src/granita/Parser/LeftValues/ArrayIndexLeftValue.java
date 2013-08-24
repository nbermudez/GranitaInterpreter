/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Parser.Expressions.Expression;
import granita.Semantic.SymbolTable.ArrayVariable;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayIndexLeftValue extends LeftValue{

    String id;
    Expression index;
    
    public ArrayIndexLeftValue(int scopeId, int line) {
        super(scopeId, line);
    }

    public ArrayIndexLeftValue(String id, Expression index, int scopeId, int line) {
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
    public String toString() {
        return id + "[" + index.toString() +"]";
    }

    @Override
    public Type validateSemantics() throws GranitaException {
        SymbolTableValue value = SymbolTableTree.getInstance().lookupFromCurrent(id);
        if (value == null){
            throw new GranitaException("undefined variable " + id + " in line "+
                    this.getLine());
        }else {
            if (!(value instanceof ArrayVariable)){
                throw new GranitaException("variable " + id + " is not an array; in line "+
                    this.getLine());
            }else {
                return ((ArrayVariable) value).getType();
            }
        }
    }
    
    
}
