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
public class BreakStatement extends Statement {
    boolean isInsideLoop;

    public BreakStatement(boolean isInsideLoop, int line) {
        super(line);
        this.isInsideLoop = isInsideLoop;
    }  

    public boolean isIsInsideLoop() {
        return isInsideLoop;
    }

    public void setIsInsideLoop(boolean isInsideLoop) {
        this.isInsideLoop = isInsideLoop;
    }    
    
    @Override
    public String toString() {
        return "break";
    }

    @Override
    public void validateSemantics() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
