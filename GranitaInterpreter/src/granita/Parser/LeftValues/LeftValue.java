/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.IR.Expressions.D_Expression;
import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class LeftValue extends Expression {
    public LeftValue(int line) {
        super(line);
    }   
    
    public abstract Type getLocation();
    public abstract void initializeVariable();
    
    @Override
    public abstract Object evaluate() throws GranitaException;
    
    @Override
    public abstract D_Expression getIR();
}
