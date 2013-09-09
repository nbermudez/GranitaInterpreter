/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Interpreter;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_While extends D_Statement {
    
    D_Expression expression;
    D_Block block;

    public D_While(D_Expression expression, D_Block block) {
        this.expression = expression;
        this.block = block;
    }
    
    @Override
    public void execute() {
        while (true) {
            Boolean ret = (Boolean) expression.evaluate();
            if (!ret) {
                break;
            }
            for (D_Statement st : block.getStatements()) {
                if (Interpreter.breakReached()) {
                    Interpreter.breakWasReached(false);
                    break;
                } else if (Interpreter.continueReached()) {
                    Interpreter.continueWasReached(false);
                    continue;
                } else {
                    st.execute();
                }
            }
        }
    }
    
}
