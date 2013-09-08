/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_For;
import granita.IR.Statements.D_Statement;
import granita.Parser.Expressions.Expression;
import granita.Types.BoolType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ForStatement extends Statement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    BlockStatement block;
    ArrayList<Expression> initializations;
    Expression termination;
    ArrayList<Statement> increments;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ForStatement(int line) {
        super(line);
        this.initializations = new ArrayList<Expression>();
        this.increments = new ArrayList<Statement>();
    }

    public ForStatement(BlockStatement block, ArrayList<Expression> initializations,
            Expression termination, ArrayList<Statement> increments, int line) {
        super(line);
        this.block = block;
        this.initializations = initializations;
        this.termination = termination;
        this.increments = increments;
    }
    //</editor-fold>    

    public void setBlock(BlockStatement st) {
        this.block = st;
    }

    public void addExpression(Expression exp) {
        this.initializations.add(exp);
    }

    public void addAssign(Statement assign) {
        this.increments.add(assign);
    }

    @Override
    public String toString() {
        String f = "for(";
        for (int i = 0; i < initializations.size() - 1; i++) {
            f += initializations.get(i).toString() + ",";
        }
        f += initializations.get(initializations.size() - 1).toString() + ";";
        f += termination.toString() + ";";
        for (int i = 0; i < increments.size() - 1; i++) {
            f += increments.get(i).toString() + ",";
        }
        f += increments.get(increments.size() - 1).toString();
        f += ")\n";
        f += block.toString();
        return f;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        /*super.validateSemantics();
        for (Expression exp : initializations) {
            exp.validateSemantics();
        }
        Type t = termination.validateSemantics();
        if (!(t instanceof BoolType)) {
            ErrorHandler.handle("for test expression must evaluate to bool: line "
                    + termination.getLine());
        }
        for (Statement exp : increments) {
            exp.validateSemantics();
        }
        block.validateSemantics();*/
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        return block.hasReturn(methodType);
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        ArrayList<D_Expression> inits = new ArrayList<D_Expression>();
        for (Expression exp : initializations) {
            inits.add(exp.getIR());
        }
        
        D_Expression terminate = termination.getIR();
        if (!(terminate.getExpressionType() instanceof BoolType)) {
            ErrorHandler.handle("for test expression must evaluate to bool: line "
                    + termination.getLine());
        }
        
        ArrayList<D_Statement> incs = new ArrayList<D_Statement>();
        for (Statement st : increments) {
            incs.add(st.getIR());
        }
        
        D_Statement blk = block.getIR();
        return new D_For(inits, terminate, incs, (D_Block) blk);
    }
}
