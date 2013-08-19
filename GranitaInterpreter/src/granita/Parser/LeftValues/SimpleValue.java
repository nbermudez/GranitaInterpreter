/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleValue extends LeftValue{
    String id;
    
    public SimpleValue(int scopeId, int line) {
        super(scopeId, line);
    }

    public SimpleValue(String id, int scopeId,  int line) {
        super(scopeId, line);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
