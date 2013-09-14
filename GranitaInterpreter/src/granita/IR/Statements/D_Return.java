/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Return extends D_Statement {

    D_Expression returnExpression;

    public D_Return(D_Expression returnExpression) {
        this.returnExpression = returnExpression;
    }
    
    @Override
    public void exec() {
        if (this.returnExpression == null) {
            Interpreter.returnWasReached(true);
        } else {
            Result ret = returnExpression.eval();
            Interpreter.setReturnValue(ret);
        }
    }
}
