/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

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
    public Type validateSemantics() throws GranitaException {
        Type LHS = left.validateSemantics();
        if (LHS == null) {
            return ErrorHandler.handle("undefined variable " + left.toString()
                    + ": line " + line);
        }
        Type RHS = right.validateSemantics();
        if (RHS == null) {
            return ErrorHandler.handle("undefined variable " + right.toString()
                    + ": line " + line);
        }

        if (LHS instanceof IntType && RHS instanceof IntType
                || LHS instanceof BoolType && RHS instanceof BoolType) {
            return new BoolType();
        } else if (LHS instanceof ErrorType || RHS instanceof ErrorType) {
            return new ErrorType();
        } else {
            return ErrorHandler.handle("operator == cannot be applied to "
                    + LHS.toString() + " and " + RHS.toString() 
                    + ": line " + line);
        }
    }
    
    @Override
    public Boolean evaluate() throws GranitaException {
        Object l =  left.evaluate();
        Object r = right.evaluate();
        
        return l.equals(r);
    }
}
