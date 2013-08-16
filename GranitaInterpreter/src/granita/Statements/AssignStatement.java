/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Expressions.Expression;
import granita.LeftValues.LeftValue;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class AssignStatement extends Statement {
    LeftValue left;
    Expression value;
    
    public AssignStatement(int line) {
        super(line);
    }

    public AssignStatement(LeftValue left, Expression value, int line) {
        super(line);
        this.left = left;
        this.value = value;
    }

    public LeftValue getLeft() {
        return left;
    }

    public void setLeft(LeftValue left) {
        this.left = left;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
    
}
