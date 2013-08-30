/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.Parser.Expressions.Expression;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class LeftValue extends Expression {
    int scopeId;
    
    public LeftValue(int scopeId, int line) {
        super(line);
        this.scopeId = scopeId;
    }   
    
    public abstract void initializeVariable();
    @Override
    public abstract Object evaluate() throws GranitaException;
}
