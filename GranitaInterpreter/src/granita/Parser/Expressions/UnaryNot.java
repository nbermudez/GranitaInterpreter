/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class UnaryNot extends Expression {
    Expression value;

    public UnaryNot(Expression value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + "!" + value.toString() + ")";
    }
    
    @Override
    public Type validateSemantics() throws GranitaException {
        Type tvalue = value.validateSemantics();
        if (tvalue == null){
            throw new GranitaException("undefined variable " + value.toString() +
                    " in line " + line);
        }
        
        if (tvalue instanceof BoolType){
            return tvalue;
        }else{
            throw new GranitaException("Operator ! cannot be applied to " + 
                    tvalue.toString() + " in line " + line);
        }
    }
}
