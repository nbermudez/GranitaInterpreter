/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_If;
import granita.IR.Statements.D_Statement;
import granita.Misc.ErrorHandler;
import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.BoolType;
import granita.Semantics.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class IfStatement extends Statement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    Expression conditional;
    BlockStatement trueBlock, falseBlock;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public IfStatement(int line) {
        super(line);
    }

    public IfStatement(Expression conditional, BlockStatement trueBlock,
            BlockStatement falseBlock, int line) {
        super(line);
        this.conditional = conditional;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public void setConditional(Expression conditional) {
        this.conditional = conditional;
    }

    public void setTrueBlock(BlockStatement trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setFalseBlock(BlockStatement falseBlock) {
        this.falseBlock = falseBlock;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        String i = "if (";
        i += conditional.toString();
        i += ")\n";
        i += trueBlock.toString();
        if (falseBlock != null) {
            i += "else";
            i += falseBlock.toString();
        }
        return i;
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        D_Expression cond = conditional.getIR();
        if (cond != null && !(cond.getExpressionType() instanceof BoolType)) {
            ErrorHandler.handle("if condition must evaluate to bool: line "
                    + conditional.getLine());
        }
        
        D_Statement tBlock = trueBlock.getIR();
        boolean b1 = SemanticUtils.getInstance().isUnreachable();
        SemanticUtils.getInstance().setUnreachable(false);
        if (tBlock != null && tBlock instanceof D_Block) {
            if (falseBlock != null) {
                D_Statement fBlock = falseBlock.getIR();
                boolean b2 = SemanticUtils.getInstance().isUnreachable();
                SemanticUtils.getInstance().setUnreachable(b1 && b2);
                return new D_If(cond, (D_Block) tBlock, (D_Block) fBlock);
            }
            return new D_If(cond, (D_Block) tBlock);
        }
        return null;
    }
}
