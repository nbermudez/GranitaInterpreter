/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.BoolResult;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_While extends D_Statement {
    
    D_Expression expression;
    D_Block block;

    public D_While(D_Expression expression, D_Block block) {
        this.expression = expression;
        this.block = block;
    }
    
    @Override
    public void exec() {
        Interpreter.registerContext(block.getContext());
        BoolResult ret = (BoolResult) expression.eval();
        while (ret.getValue()) {
            for (D_Statement st : block.getStatements()) {
                if (Interpreter.breakReached() || Interpreter.continueReached()) {
                    break;
                } else {
                    st.exec();
                }
            }
            if (Interpreter.breakReached()) {
                Interpreter.breakWasReached(false);
                break;
            } else if (Interpreter.continueReached()) {
                Interpreter.continueWasReached(false);
            }
            ret = (BoolResult) expression.eval();
        }
        Interpreter.unregisterContext();
    }
}
