/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granitainterpreter.AstNode;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ForStatement extends Statement {

    AstNode block;
    ArrayList<AstNode> expressions;
    AstNode conditional;
    ArrayList<AstNode> assigns;

    public ForStatement(int line) {
        super(line);
        this.expressions = new ArrayList<AstNode>();
        this.assigns = new ArrayList<AstNode>();
    }

    public ForStatement(AstNode block, ArrayList<AstNode> expressions, 
            AstNode conditional, ArrayList<AstNode> assigns, int line) {
        super(line);
        this.block = block;
        this.expressions = expressions;
        this.conditional = conditional;
        this.assigns = assigns;
    }

    public void setBlock(AstNode st) {
        this.block = st;
    }

    public void addExpression(AstNode exp) {
        this.expressions.add(exp);
    }

    public void addAssign(AstNode assign) {
        this.assigns.add(assign);
    }

    public void setConditional(AstNode conditional) {
        this.conditional = conditional;
    }
}
