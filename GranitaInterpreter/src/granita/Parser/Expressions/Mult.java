/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.IR.Expressions.D_Mult;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Mult extends BinaryExpression {

    public Mult(Expression left, Expression right, int line) {
        super(left, right, line);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
    
    @Override
    public D_Expression getIR() {
        D_Expression LHS = left.getIR();
        D_Expression RHS = right.getIR();
        if (RHS == null || LHS == null) {
            return null;
        }
        
        Type rType = RHS.getExpressionType(), lType = LHS.getExpressionType();
        if (rType instanceof IntType && lType instanceof IntType) {
            return new D_Mult(LHS, RHS);
        } else if (lType instanceof ErrorType || rType instanceof ErrorType) {
            return new D_Mult(LHS, RHS);
        } else {
            ErrorHandler.handle("operator * cannot be applied to "
                    + lType.toString() + " and " + rType.toString()
                    + ": line " + line);
            return null;
        }
    }
}
