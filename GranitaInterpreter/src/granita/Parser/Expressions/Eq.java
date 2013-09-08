/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Eq;
import granita.IR.Expressions.D_Expression;
import granita.Types.BoolType;
import granita.Types.ErrorType;
import granita.Types.IntType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Eq extends BinaryExpression {
    
    public Eq(Expression left, Expression right, int line) {
        super(left, right, line);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " == " + right.toString() + ")";
    }
    
    @Override
    public D_Expression getIR() {
        D_Expression LHS = left.getIR();
        D_Expression RHS = right.getIR();
        if (RHS == null || LHS == null) {
            return null;
        }
        
        Type rType = RHS.getExpressionType(), lType = LHS.getExpressionType();
        if (rType instanceof IntType && lType instanceof IntType
                || lType instanceof BoolType && rType instanceof BoolType) {
            return new D_Eq(lType, LHS, RHS);
        } else if (lType instanceof ErrorType || rType instanceof ErrorType) {
            return new D_Eq(new ErrorType(), LHS, RHS);
        } else {
            ErrorHandler.handle("operator == cannot be applied to "
                    + lType.toString() + " and " + rType.toString() 
                    + ": line " + line);
            return null;
        }
    }
}
