/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Results.BoolResult;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_For extends D_Statement {

    ArrayList<D_Expression> initializations;
    D_Expression termination;
    ArrayList<D_Statement> increments;
    D_Block block;

    public D_For(ArrayList<D_Expression> initializations, D_Expression termination,
            ArrayList<D_Statement> increments, D_Block block) {
        this.initializations = initializations;
        this.termination = termination;
        this.increments = increments;
        this.block = block;
    }

    @Override
    public void execute() {
        while (true) {
            for (D_Expression expression : initializations) {
                expression.evaluate();
            }
            Boolean ret = (Boolean) termination.evaluate();
            if (!ret) {
                break;
            }
            for (D_Statement st : block.getStatements()) {
                if (st instanceof D_Continue) {
                    continue;
                } else if (st instanceof D_Break) {
                    break;
                } else {
                    st.execute();
                }
            }
            for (D_Statement st : increments) {
                st.execute();
            }
        }
    }

    @Override
    public void exec() {
        for (D_Expression expression : initializations) {
            expression.eval();
        }
        while (true) {
            BoolResult ret = (BoolResult) termination.eval();
            if (!ret.getValue()) {
                break;
            }
            for (D_Statement st : block.getStatements()) {
                if (st instanceof D_Continue) {
                    continue;
                } else if (st instanceof D_Break) {
                    break;
                } else {
                    st.exec();
                }
            }
            for (D_Statement st : increments) {
                st.exec();
            }
        }
    }
}
