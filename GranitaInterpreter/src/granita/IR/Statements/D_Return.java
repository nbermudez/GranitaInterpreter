/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;
import granita.Types.BoolType;
import granita.Types.IntType;
import granita.Types.Type;

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
    public void execute() {
        if (this.returnExpression == null) {
            Interpreter.returnWasReached(true);
        } else {
            Type returnType = this.returnExpression.getExpressionType();
            if (returnType instanceof BoolType || returnType instanceof IntType) {
                Interpreter.returnedValue(returnExpression.evaluate());
            } else {
                Interpreter.returnWasReached(true);
            }
        }
    }
    
    @Override
    public void exec() {
        if (this.returnExpression == null) {
            Interpreter.returnWasReached(true);
        } else {
            Result ret = returnExpression.eval();
            Interpreter.setReturnValue(ret);
            //int contextId = Interpreter.currentContext().getReturnValueContext();
            //Interpreter.currentContext().setVariableInRE(contextId, 0, ret);
        }
    }
}
