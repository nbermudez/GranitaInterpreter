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
import granitainterpreter.Interpreter;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ForStatement extends Statement {

    BlockStatement block;
    ArrayList<Expression> initializations;
    Expression termination;
    ArrayList<Statement> increments;

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
        super.validateSemantics();
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
        block.validateSemantics();
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        return block.hasReturn(methodType);
    }

    @Override
    public void execute() throws GranitaException {
        while (true) {
            for (Expression expression : initializations) {
                expression.evaluate();
            }
            Boolean ret = (Boolean) termination.evaluate();
            if (!ret) {
                break;
            }
            Interpreter.getInstance().pushBlockToFunction(block);
            for (Statement st : block.getStatements()) {
                if (st instanceof ContinueStatement) {
                    continue;
                } else if (st instanceof BreakStatement) {
                    break;
                } else {
                    st.execute();
                }
            }
            Interpreter.getInstance().popBlockToFunction();
            for (Statement st : increments) {
                st.execute();
            }
        }
    }    
}
