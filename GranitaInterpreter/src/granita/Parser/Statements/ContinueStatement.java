/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Statements.D_Continue;
import granita.IR.Statements.D_Statement;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ContinueStatement extends Statement {
    boolean isInsideLoop;

    public ContinueStatement(boolean isInsideLoop, int line) {
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
        return "continue;";
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        if (!isInsideLoop) {
            ErrorHandler.handle("continue statement must be inside a loop");
        }
        SemanticUtils.getInstance().setUnreachableStatement();
    }

    @Override
    public D_Statement getIR() {
        return new D_Continue();
    }
    
}
