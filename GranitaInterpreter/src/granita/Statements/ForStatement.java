/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Expressions.Expression;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ForStatement extends Statement {

    Statement block;
    ArrayList<Expression> initializations;
    Expression termination; 
    ArrayList<Statement> increments; 

    public ForStatement(int line) {
        super(line);
        this.initializations = new ArrayList<Expression>();
        this.increments = new ArrayList<Statement>();
    }

    public ForStatement(Statement block, ArrayList<Expression> initializations, 
            Expression termination, ArrayList<Statement> increments, int line) {
        super(line);
        this.block = block;
        this.initializations = initializations;
        this.termination = termination;
        this.increments = increments;
    }

    public void setBlock(Statement st) {
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
        for (int i = 0; i< initializations.size() - 1 ; i++){
            f += initializations.get(i).toString() + ",";
        }
        f += initializations.get(initializations.size() - 1).toString() + ";";
        f += termination.toString() + ";";
        for (int i = 0; i< increments.size() - 1 ; i++){
            f += increments.get(i).toString() + ",";
        }
        f += increments.get(increments.size() - 1).toString() + ";";
        f += ")\n";
        f += block.toString();
        return f;
    }
}
