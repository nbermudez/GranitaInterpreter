/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Expressions.Expression;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IfStatement extends Statement{
    
    Expression conditional;
    Statement trueBlock, falseBlock;
    
    public IfStatement(int line){
        super(line);
    }
    
    public IfStatement(Expression conditional, Statement trueBlock, 
            Statement falseBlock, int line){
        super(line);
        this.conditional = conditional;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    public void setConditional(Expression conditional) {
        this.conditional = conditional;
    }

    public void setTrueBlock(Statement trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setFalseBlock(Statement falseBlock) {
        this.falseBlock = falseBlock;
    }    

    @Override
    public String toString() {
        String i = "if (";
        i += conditional.toString();
        i += ")\n";
        i += trueBlock.toString();
        if (falseBlock != null){
            i += "else";
            i += falseBlock.toString();
        }
        return i;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        conditional.validateSemantics();
        trueBlock.validateSemantics();
        if (falseBlock != null){
            falseBlock.validateSemantics();
        }
    }
}
