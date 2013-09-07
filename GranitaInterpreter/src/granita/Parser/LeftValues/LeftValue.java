/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.LeftValues;

import granita.IR.LeftValues.D_LeftValue;
import granita.Parser.Expressions.Expression;
import granita.Types.Type;
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
    public abstract D_LeftValue getIR();
}
