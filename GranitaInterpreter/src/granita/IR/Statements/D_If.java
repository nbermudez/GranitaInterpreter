/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Results.BoolResult;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_If extends D_Statement {
    D_Expression conditional;
    D_Block trueBlock, falseBlock;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public D_If(D_Expression conditional, D_Block trueBlock, D_Block falseBlock) {
        this.conditional = conditional;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    public D_If(D_Expression conditional, D_Block trueBlock) {
        this.conditional = conditional;
        this.trueBlock = trueBlock;
        this.falseBlock = null;
    }
    //</editor-fold>    
    
    @Override
    public void exec() {
        BoolResult ret = (BoolResult) conditional.eval();
        if (ret.getValue()) {
            trueBlock.exec();
        } else if (falseBlock != null) {
            falseBlock.exec();
        }
    }
}
