/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class Expression {
    int line;
    
    public Expression(int line) {
        this.line = line;
    }
    
    public abstract Type validateSemantics() throws GranitaException;
}
