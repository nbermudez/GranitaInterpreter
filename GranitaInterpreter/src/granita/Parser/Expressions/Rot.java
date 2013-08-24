/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Rot extends BinaryExpression {

    public Rot(Expression left, Expression right, int line) {
        super(left, right, line);
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + " rot " + right.toString() + ")";
    }
    
    @Override
    public Type validateSemantics() throws GranitaException {
        Type LHS = left.validateSemantics();
        if (LHS == null){
            throw new GranitaException("undefined variable " + left.toString() + 
                    " in line " + line);
        }
        Type RHS = right.validateSemantics();
        if (RHS == null){
            throw new GranitaException("undefined variable " + right.toString() + 
                    " in line " + line);
        }
        
        if (LHS instanceof IntType && RHS instanceof IntType){
            return LHS;
        }else{
            throw new GranitaException("Operator rot cannot be applied to " + 
                    LHS.toString() + " and " + RHS.toString() + " in line " + line);
        }
    }
}
