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

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    ArrayList<D_Statement> initializations;
    D_Expression termination;
    ArrayList<D_Statement> increments;
    D_Block block;
    //</editor-fold>    

    public D_For(ArrayList<D_Statement> initializations, D_Expression termination,
            ArrayList<D_Statement> increments, D_Block block) {
        this.initializations = initializations;
        this.termination = termination;
        this.increments = increments;
        this.block = block;
    }
    
    @Override
    public void exec() {
        for (D_Statement init : initializations) {
            init.exec();
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
