/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granitainterpreter.AstNode;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IfStatement extends Statement{
    
    AstNode conditional;
    AstNode trueBlock, falseBlock;
    
    public IfStatement(){
    }
    
    public IfStatement(AstNode conditional, AstNode trueBlock, AstNode falseBlock){
        this.conditional = conditional;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    public void setConditional(AstNode conditional) {
        this.conditional = conditional;
    }

    public void setTrueBlock(AstNode trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setFalseBlock(AstNode falseBlock) {
        this.falseBlock = falseBlock;
    }    
    
}
