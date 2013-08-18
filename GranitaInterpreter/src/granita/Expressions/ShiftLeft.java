/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ShiftLeft extends BinaryExpression {

    public ShiftLeft(Expression left, Expression right, int line) {
        super(left, right, line);
    }
    
    @Override
    public String toString() {
        return left.toString() + " << " + right.toString();
    }
    
    @Override
    public Type validateSemantics() throws GranitaException {
        Type LHS = left.validateSemantics();
        Type RHS = right.validateSemantics();
        
        if (LHS instanceof IntType && RHS instanceof IntType){
            return LHS;
        }else{
            throw new GranitaException("Operator << cannot be applied to " + 
                    LHS.toString() + " and " + RHS.toString() + " in line " + line);
        }
    }
}
