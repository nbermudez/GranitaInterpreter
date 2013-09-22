/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.IR.Expressions.D_Or;
import granita.Misc.ErrorHandler;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class Or extends BinaryExpression {

    public Or(Expression left, Expression right, int line) {
        super(left, right, line);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " || " + right.toString() + ")";
    }

    @Override
    public D_Expression getIR() {
        D_Expression LHS = left.getIR();
        D_Expression RHS = right.getIR();
        if (RHS == null || LHS == null) {
            return null;
        }
        
        Type rType = RHS.getExpressionType(), lType = LHS.getExpressionType();
        if (rType instanceof BoolType && lType instanceof BoolType) {
            return new D_Or(LHS, RHS);
        } else if (lType instanceof ErrorType || rType instanceof ErrorType) {
            return new D_Or(LHS, RHS);
        } else {
            ErrorHandler.handle("operator || cannot be applied to "
                    + LHS.toString() + " and " + RHS.toString()
                    + ": line " + line);
            return null;
        }
    }
}
