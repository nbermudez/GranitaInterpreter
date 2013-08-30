/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class WhileStatement extends Statement{
    
    Expression exp;
    BlockStatement block;
    
    public WhileStatement(int line){
        super(line);
    }
    
    public WhileStatement(Expression exp, BlockStatement block, int line){
        super(line);
        this.exp = exp;
        this.block = block;
    }
    
    public void setExpression(Expression exp){
        this.exp = exp;
    }
    
    public void setBlock(BlockStatement block){
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

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        Type rtype = exp.validateSemantics();
        if (!(rtype instanceof BoolType)) {
            ErrorHandler.handle("while condition must evaluate to bool: line " + this.getLine());
        }
        block.validateSemantics();
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        return block.hasReturn(methodType);
    }

    @Override
    public void execute() throws GranitaException {
        while (true) {
            Boolean ret = (Boolean) exp.evaluate();
            if (!ret) {
                break;
            }
            for (Statement st : block.getStatements()) {
                if (st instanceof ContinueStatement) {
                    continue;
                } else if (st instanceof BreakStatement) {
                    break;
                } else {
                    st.execute();
                }
            }
        }
    }
    
}
