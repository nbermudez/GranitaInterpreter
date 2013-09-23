/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Misc.ErrorHandler;
import granita.Misc.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_Mod extends D_BinaryExpression {

    public D_Mod(D_Expression left, D_Expression right) {
        super(left, right);
    }
    
    @Override
    public Result eval() {
        IntResult l = (IntResult) left.eval();        
        IntResult r = (IntResult) right.eval();
        
        if (r.getValue() == 0) {
            try {
                ErrorHandler.handleFatalError("division by zero caused by mod operation!");
                return null;
            } catch (GranitaException ex) {
            }
        }
        
        return new IntResult(l.getValue() % r.getValue());
    }
}
