/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Functions;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.Results.Result;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_Argument extends D_Expression {
    D_Expression value;

    public D_Argument(D_Expression value) {
        this.value = value;
    }

    @Override
    public Result eval() {
        return value.eval();
    }
    
}
