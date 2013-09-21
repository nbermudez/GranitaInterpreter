/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.Result;
import granita.Interpreter.Results.StringResult;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_LitString extends D_Expression {
    StringResult value;
    
    public D_LitString(String value) {
        this.value = new StringResult(value);
    }

    @Override
    public Result eval() {
        return value;
    }
    
}
