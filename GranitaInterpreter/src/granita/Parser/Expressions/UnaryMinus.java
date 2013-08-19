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
public class UnaryMinus extends Expression {
    Expression value;

    public UnaryMinus(Expression value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public String toString() {
        return "-" + value.toString();
    }
    
    @Override
    public Type validateSemantics() throws GranitaException {
        Type tvalue = value.validateSemantics();
        
        if (tvalue instanceof IntType){
            return tvalue;
        }else{
            throw new GranitaException("Operator - cannot be applied to " + 
                    tvalue.toString() + " in line " + line);
        }
    }
}
