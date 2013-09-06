/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Statements.D_Statement;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class Statement implements Cloneable {
    int line;
    
    public Statement(int line) {
        this.line = line;
    }    

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }    
    
    public void validateSemantics() throws GranitaException {
        if (SemanticUtils.getInstance().isUnreachableStatement() == 1) {
            ErrorHandler.handle("unreachable statement: line " + this.getLine());
            SemanticUtils.getInstance().setUnreachableStatement();
        }
    }
    
    public Type hasReturn(Type methodType) throws GranitaException {
        return null;
    }
    
    public void execute() throws GranitaException {}
    
    public D_Statement getIR(){return null;}
}