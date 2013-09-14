/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Statements.D_Break;
import granita.IR.Statements.D_Statement;
import granita.Misc.ErrorHandler;
import granita.Semantics.SemanticUtils;

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
    public D_Statement getIR() {
        checkForUnreachableStatement();
        if (!isInsideLoop) {
            ErrorHandler.handle("break statement must be inside a loop");
        }
        SemanticUtils.getInstance().setUnreachableStatement();
        return new D_Break();
    }
}
