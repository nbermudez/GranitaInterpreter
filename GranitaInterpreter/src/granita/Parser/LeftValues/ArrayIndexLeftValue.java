/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Parser.Expressions.Expression;
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

    @Override
    public String toString() {
        return id + "[" + index.toString() +"]";
    }

    @Override
    public Type validateSemantics() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
