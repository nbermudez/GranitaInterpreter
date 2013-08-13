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
public class WhileStatement extends Statement{
    
    AstNode exp;
    AstNode block;
    
    public WhileStatement(){
    
    }
    
    public WhileStatement(AstNode exp, AstNode block){
        this.exp = exp;
        this.block = block;
    }
    
    public void setExpression(AstNode exp){
        this.exp = exp;
    }
    
    public void setBlock(Statement block){
        this.block = block;
    }
}
