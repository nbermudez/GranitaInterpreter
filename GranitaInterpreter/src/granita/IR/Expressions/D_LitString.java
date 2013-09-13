/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.Result;
import granita.Interpreter.Results.StringResult;
import granita.Types.StringType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_LitString extends D_Expression {

    public D_LitString(String value) {
        this.expressionType = new StringType(value);
    }
    
    @Override
    public Object evaluate() {
        return this.expressionType.getValue();
    }

    @Override
    public Result eval() {
        return new StringResult((String)this.expressionType.getValue());
    }
    
}
