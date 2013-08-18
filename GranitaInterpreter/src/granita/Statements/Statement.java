/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class Statement {
    int line;
    
    public Statement(int line) {
        this.line = line;
    }    
    
    public abstract void validateSemantics() throws GranitaException;
}