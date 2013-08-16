/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Expressions.Expression;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class WhileStatement extends Statement{
    
    Expression exp;
    Statement block;
    
    public WhileStatement(int line){
        super(line);
    }
    
    public WhileStatement(Expression exp, Statement block, int line){
        super(line);
        this.exp = exp;
        this.block = block;
    }
    
    public void setExpression(Expression exp){
        this.exp = exp;
    }
    
    public void setBlock(Statement block){
        this.block = block;
    }

    @Override
    public String toString() {
        String w = "while (";
        w += exp.toString();
        w += ")";
        w += block.toString();
        
        return w;
    }
    
}
